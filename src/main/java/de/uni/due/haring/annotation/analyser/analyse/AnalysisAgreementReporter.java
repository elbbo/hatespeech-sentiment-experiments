package de.uni.due.haring.annotation.analyser.analyse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.uima.UIMAException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCopier;
import org.dkpro.statistics.agreement.coding.CodingAnnotationStudy;
import org.dkpro.statistics.agreement.coding.CohenKappaAgreement;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.analyser.annotations.AnnotationUnit;
import de.uni.due.haring.annotation.analyser.services.AnnotatorJCasService;
import webanno.custom.Zielgruppenadressierung;

public class AnalysisAgreementReporter extends JCasAnnotator_ImplBase {

    public static final String PARAM_TYPESYSTEM_DESCRIPTION_PATH = "typeSystemDescriptionPath";
    @ConfigurationParameter(name = PARAM_TYPESYSTEM_DESCRIPTION_PATH, mandatory = true)
    private String typeSystemDescriptionPath;

    private TypeSystemDescription localDescription;

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
	super.initialize(context);
	localDescription = TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(typeSystemDescriptionPath);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
	try {
	    DocumentMetaData documentMetaData = (DocumentMetaData) jCas.getDocumentAnnotationFs();
	    if (documentMetaData.getDocumentId().startsWith("anno1")) {
		JCas anno1JCas = JCasFactory.createJCas(localDescription);
		DocumentMetaData.copy(jCas, anno1JCas);
		CasCopier.copyCas(jCas.getCas(), anno1JCas.getCas(), true);
		AnnotatorJCasService.setAnno1JCas(anno1JCas);
	    }
	    if (documentMetaData.getDocumentId().startsWith("anno2")) {
		JCas anno2JCas = JCasFactory.createJCas(localDescription);
		DocumentMetaData.copy(jCas, anno2JCas);
		CasCopier.copyCas(jCas.getCas(), anno2JCas.getCas(), true);
		AnnotatorJCasService.setAnno2JCas(anno2JCas);
	    }
	} catch (UIMAException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void collectionProcessComplete() throws AnalysisEngineProcessException {
	List<AnnotationUnit> annotationUnits = new ArrayList<>();
	processAnnotator1Annotations(annotationUnits);
	processAnnotator2Annotations(annotationUnits);

	CodingAnnotationStudy studyFullAgreement = new CodingAnnotationStudy(2);
	CodingAnnotationStudy studyMatchingAgreement = new CodingAnnotationStudy(2);

	for (AnnotationUnit annotationUnit : annotationUnits) {
	    if (annotationUnit.isMatch()) {
		if (annotationUnit.getAnno1Layer1Features().size() >= annotationUnit.getAnno2Layer1Features().size()) {
		    List<String> annotator2FeaturesTmp = new ArrayList<>(annotationUnit.getAnno2Layer1Features());
		    for (String annotationFeature : annotationUnit.getAnno1Layer1Features()) {
			if (annotator2FeaturesTmp.contains(annotationFeature)) {
			    studyFullAgreement.addItem(annotationFeature, annotationFeature);
			    studyMatchingAgreement.addItem(annotationFeature, annotationFeature);
			    annotator2FeaturesTmp.remove(annotationFeature);
			} else {
			    // add disagreement
			    studyFullAgreement.addItem("Group", "Individual");
			    studyMatchingAgreement.addItem("Group", "Individual");
			}
		    }
		} else {
		    List<String> annotator1FeaturesTmp = new ArrayList<>(annotationUnit.getAnno1Layer1Features());
		    for (String annotationFeature : annotationUnit.getAnno2Layer1Features()) {
			if (annotator1FeaturesTmp.contains(annotationFeature)) {
			    studyFullAgreement.addItem(annotationFeature, annotationFeature);
			    studyMatchingAgreement.addItem(annotationFeature, annotationFeature);
			    annotator1FeaturesTmp.remove(annotationFeature);
			} else {
			    // add disagreement
			    studyFullAgreement.addItem("Group", "Individual");
			    studyMatchingAgreement.addItem("Group", "Individual");
			}
		    }
		}

	    } else if (annotationUnit.isAnno1Present()) {
		for (String feature : annotationUnit.getAnno1Layer1Features()) {
		    // add disagreement
		    studyFullAgreement.addItem("Group", "Individual");
		}
	    } else if (annotationUnit.isAnno2Present()) {
		for (String feature : annotationUnit.getAnno2Layer1Features()) {
		    // add disagreement
		    studyFullAgreement.addItem("Group", "Individual");
		}
	    }
	}

	CohenKappaAgreement kappaFull = new CohenKappaAgreement(studyFullAgreement);
	CohenKappaAgreement kappaMatching = new CohenKappaAgreement(studyMatchingAgreement);
	double agreementFull = kappaFull.calculateAgreement();
	double agreementMatching = kappaMatching.calculateAgreement();
	System.out.println("Full Agreement: " + agreementFull);
	System.out.println("Matching Agreement: " + agreementMatching);

	System.out.println(annotationUnits.size());

    }

    private void processAnnotator1Annotations(List<AnnotationUnit> annotationUnits) {
	for (Sentence sentence : JCasUtil.select(AnnotatorJCasService.getAnno1JCas(), Sentence.class)) {
	    for (Zielgruppenadressierung personAddressAnno1 : JCasUtil.subiterate(AnnotatorJCasService.getAnno1JCas(),
		    Zielgruppenadressierung.class, sentence, true, true)) {
		List<AnnotationUnit> presentAnnotation = annotationUnits.stream()
			.filter(annotationUnit -> annotationUnit.getBegin() == personAddressAnno1.getBegin()
				&& annotationUnit.getEnd() == personAddressAnno1.getEnd())
			.collect(Collectors.toList());

		if (presentAnnotation.isEmpty()) {
		    AnnotationUnit annotationUnit = new AnnotationUnit();
		    annotationUnit.setBegin(personAddressAnno1.getBegin());
		    annotationUnit.setEnd(personAddressAnno1.getEnd());
		    annotationUnit.setAnno1Present(true);
		    annotationUnit.getAnno1Layer1Features().add(personAddressAnno1.getGroupIndividual());

		    annotationUnits.add(annotationUnit);
		} else {
		    presentAnnotation.get(0).getAnno1Layer1Features().add(personAddressAnno1.getGroupIndividual());
		}
	    }
	}
    }

    private void processAnnotator2Annotations(List<AnnotationUnit> annotationUnits) {
	for (Sentence sentence : JCasUtil.select(AnnotatorJCasService.getAnno2JCas(), Sentence.class)) {
	    for (Zielgruppenadressierung personAddressAnno2 : JCasUtil.subiterate(AnnotatorJCasService.getAnno2JCas(),
		    Zielgruppenadressierung.class, sentence, true, true)) {
		List<AnnotationUnit> presentAnnotation = annotationUnits.stream()
			.filter(annotationUnit -> annotationUnit.getBegin() == personAddressAnno2.getBegin()
				&& annotationUnit.getEnd() == personAddressAnno2.getEnd())
			.collect(Collectors.toList());

		if (presentAnnotation.isEmpty()) {
		    AnnotationUnit annotationUnit = new AnnotationUnit();
		    annotationUnit.setBegin(personAddressAnno2.getBegin());
		    annotationUnit.setEnd(personAddressAnno2.getEnd());
		    annotationUnit.setAnno2Present(true);
		    annotationUnit.getAnno1Layer1Features().add(personAddressAnno2.getGroupIndividual());

		    annotationUnits.add(annotationUnit);
		} else {
		    if (!presentAnnotation.get(0).isAnno2Present())
			presentAnnotation.get(0).setAnno2Present(true);
		    presentAnnotation.get(0).getAnno2Layer1Features().add(personAddressAnno2.getGroupIndividual());
		}
	    }
	}
    }
}
