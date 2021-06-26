package de.uni.due.haring.annotation.analyser.analyse;

import java.text.DecimalFormat;
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
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;
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
	AnnotatorJCasService.initialize();
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
	try {
	    DocumentMetaData documentMetaData = (DocumentMetaData) jCas.getDocumentAnnotationFs();
	    if (documentMetaData.getDocumentId().startsWith("anno1")) {
		JCas anno1JCas = JCasFactory.createJCas(localDescription);
		DocumentMetaData.copy(jCas, anno1JCas);
		CasCopier.copyCas(jCas.getCas(), anno1JCas.getCas(), true);
		AnnotatorJCasService.addAnno1JCas(anno1JCas);
	    }
	    if (documentMetaData.getDocumentId().startsWith("anno2")) {
		JCas anno2JCas = JCasFactory.createJCas(localDescription);
		DocumentMetaData.copy(jCas, anno2JCas);
		CasCopier.copyCas(jCas.getCas(), anno2JCas.getCas(), true);
		AnnotatorJCasService.addAnno2JCas(anno2JCas);
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

	CodingAnnotationStudy studyFullAgreementGroupAffiliation = new CodingAnnotationStudy(2);
	CodingAnnotationStudy studyMatchingAgreementGroupAffiliation = new CodingAnnotationStudy(2);

	CodingAnnotationStudy studyFullAgreementNegativeSentiment = new CodingAnnotationStudy(2);
	CodingAnnotationStudy studyMatchingAgreementNegativeSentiment = new CodingAnnotationStudy(2);

	CodingAnnotationStudy studyFullAgreementImplicitAddressing = new CodingAnnotationStudy(2);
	CodingAnnotationStudy studyMatchingAgreementImplicitAddressing = new CodingAnnotationStudy(2);

	int countAgreedAnnotations = 0;
	int countDisagreedAnno1 = 0;
	int countDisagreedAnno2 = 0;

	for (AnnotationUnit annotationUnit : annotationUnits) {
	    if (annotationUnit.isMatch()) {
		if (annotationUnit.getAnno1Layer1Features().size() >= annotationUnit.getAnno2Layer1Features().size()) {
		    countAgreedAnnotations++;
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

		    List<String> annotator2FeaturesLayer2Tmp = new ArrayList<>(annotationUnit.getAnno2Layer2Features());
		    for (String annotationFeature : annotationUnit.getAnno1Layer2Features()) {
			if (annotator2FeaturesLayer2Tmp.contains(annotationFeature)) {
			    studyFullAgreementGroupAffiliation.addItem(annotationFeature, annotationFeature);
			    studyMatchingAgreementGroupAffiliation.addItem(annotationFeature, annotationFeature);
			    annotator2FeaturesLayer2Tmp.remove(annotationFeature);
			} else {
			    // add disagreement
			    studyFullAgreementGroupAffiliation.addItem(GroupAffiliationType.OTHER,
				    GroupAffiliationType.POLICE);
			    studyMatchingAgreementGroupAffiliation.addItem(GroupAffiliationType.OTHER,
				    GroupAffiliationType.POLICE);
			}
		    }

		    List<Boolean> annotator2FeaturesLayer3Tmp = new ArrayList<>(
			    annotationUnit.getAnno2Layer3Features());
		    for (Boolean annotationFeature : annotationUnit.getAnno1Layer3Features()) {
			if (annotator2FeaturesLayer3Tmp.contains(annotationFeature)) {
			    studyFullAgreementNegativeSentiment.addItem(annotationFeature, annotationFeature);
			    studyMatchingAgreementNegativeSentiment.addItem(annotationFeature, annotationFeature);
			    annotator2FeaturesLayer3Tmp.remove(annotationFeature);
			} else {
			    // add disagreement
			    studyFullAgreementNegativeSentiment.addItem(true, false);
			    studyMatchingAgreementNegativeSentiment.addItem(true, false);
			}
		    }

		    List<Boolean> annotator2FeaturesLayer4Tmp = new ArrayList<>(
			    annotationUnit.getAnno2Layer4Features());
		    for (Boolean annotationFeature : annotationUnit.getAnno1Layer4Features()) {
			if (annotator2FeaturesLayer4Tmp.contains(annotationFeature)) {
			    studyFullAgreementImplicitAddressing.addItem(annotationFeature, annotationFeature);
			    studyMatchingAgreementImplicitAddressing.addItem(annotationFeature, annotationFeature);
			    annotator2FeaturesLayer4Tmp.remove(annotationFeature);
			} else {
			    // add disagreement
			    studyFullAgreementImplicitAddressing.addItem(true, false);
			    studyMatchingAgreementImplicitAddressing.addItem(true, false);
			}
		    }

		} else {
		    countAgreedAnnotations++;
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

		    List<String> annotator1FeaturesLayer2Tmp = new ArrayList<>(annotationUnit.getAnno1Layer2Features());
		    for (String annotationFeature : annotationUnit.getAnno2Layer2Features()) {
			if (annotator1FeaturesLayer2Tmp.contains(annotationFeature)) {
			    studyFullAgreementGroupAffiliation.addItem(annotationFeature, annotationFeature);
			    studyMatchingAgreementGroupAffiliation.addItem(annotationFeature, annotationFeature);
			    annotator1FeaturesLayer2Tmp.remove(annotationFeature);
			} else {
			    // add disagreement
			    studyFullAgreementGroupAffiliation.addItem(GroupAffiliationType.OTHER,
				    GroupAffiliationType.POLICE);
			    studyMatchingAgreementGroupAffiliation.addItem(GroupAffiliationType.OTHER,
				    GroupAffiliationType.POLICE);
			}
		    }

		    List<Boolean> annotator1FeaturesLayer3Tmp = new ArrayList<>(
			    annotationUnit.getAnno1Layer3Features());
		    for (Boolean annotationFeature : annotationUnit.getAnno2Layer3Features()) {
			if (annotator1FeaturesLayer3Tmp.contains(annotationFeature)) {
			    studyFullAgreementNegativeSentiment.addItem(annotationFeature, annotationFeature);
			    studyMatchingAgreementNegativeSentiment.addItem(annotationFeature, annotationFeature);
			    annotator1FeaturesLayer3Tmp.remove(annotationFeature);
			} else {
			    // add disagreement
			    studyFullAgreementNegativeSentiment.addItem(true, false);
			    studyMatchingAgreementNegativeSentiment.addItem(true, false);
			}
		    }

		    List<Boolean> annotator1FeaturesLayer4Tmp = new ArrayList<>(
			    annotationUnit.getAnno1Layer4Features());
		    for (Boolean annotationFeature : annotationUnit.getAnno2Layer4Features()) {
			if (annotator1FeaturesLayer4Tmp.contains(annotationFeature)) {
			    studyFullAgreementImplicitAddressing.addItem(annotationFeature, annotationFeature);
			    studyMatchingAgreementImplicitAddressing.addItem(annotationFeature, annotationFeature);
			    annotator1FeaturesLayer4Tmp.remove(annotationFeature);
			} else {
			    // add disagreement
			    studyFullAgreementImplicitAddressing.addItem(true, false);
			    studyMatchingAgreementImplicitAddressing.addItem(true, false);
			}
		    }
		}

	    } else if (annotationUnit.isAnno1Present()) {
		countDisagreedAnno1++;
		for (String feature : annotationUnit.getAnno1Layer1Features()) {
		    // add disagreement
		    studyFullAgreement.addItem("Group", "Individual");
		    studyFullAgreementGroupAffiliation.addItem(GroupAffiliationType.OTHER, GroupAffiliationType.POLICE);
		    studyFullAgreementNegativeSentiment.addItem(true, false);
		    studyFullAgreementImplicitAddressing.addItem(true, false);
		}
	    } else if (annotationUnit.isAnno2Present()) {
		countDisagreedAnno2++;
		for (String feature : annotationUnit.getAnno2Layer1Features()) {
		    // add disagreement
		    studyFullAgreement.addItem("Group", "Individual");
		    studyFullAgreementGroupAffiliation.addItem(GroupAffiliationType.OTHER, GroupAffiliationType.POLICE);
		    studyFullAgreementNegativeSentiment.addItem(true, false);
		    studyFullAgreementImplicitAddressing.addItem(true, false);
		}
	    }
	}

	CohenKappaAgreement kappaFull = new CohenKappaAgreement(studyFullAgreement);
	CohenKappaAgreement kappaMatching = new CohenKappaAgreement(studyMatchingAgreement);
	double agreementFull = kappaFull.calculateAgreement();
	double agreementMatching = kappaMatching.calculateAgreement();

	AppPrintService.printAnnotatorAgreement("Determination of the agreement on layer 1 ...");
	AppPrintService.printAnnotatorAgreement("Full Agreement for Individual/Group: " + agreementFull);
	AppPrintService.printAnnotatorAgreement("Matching Agreement for Individual/Group: " + agreementMatching);

	CohenKappaAgreement kappaFullGroupAffiliation = new CohenKappaAgreement(studyFullAgreementGroupAffiliation);
	CohenKappaAgreement kappaMatchingGroupAffiliation = new CohenKappaAgreement(
		studyMatchingAgreementGroupAffiliation);
	double agreementFullGroupAffiliation = kappaFullGroupAffiliation.calculateAgreement();
	double agreementMatchingGroupAffiliation = kappaMatchingGroupAffiliation.calculateAgreement();

	AppPrintService.printAnnotatorAgreement("Determination of the agreement on layer 2 ...");
	AppPrintService
		.printAnnotatorAgreement("Full Agreement for GroupAffiliation: " + agreementFullGroupAffiliation);
	AppPrintService.printAnnotatorAgreement(
		"Matching Agreement for GroupAffiliation: " + agreementMatchingGroupAffiliation);

	CohenKappaAgreement kappaFullNegativeSentiment = new CohenKappaAgreement(studyFullAgreementNegativeSentiment);
	CohenKappaAgreement kappaMatchingNegativeSentiment = new CohenKappaAgreement(
		studyMatchingAgreementNegativeSentiment);
	double agreementFullNegativeSentiment = kappaFullNegativeSentiment.calculateAgreement();
	double agreementMatchingNegativeSentiment = kappaMatchingNegativeSentiment.calculateAgreement();

	AppPrintService.printAnnotatorAgreement("Determination of the agreement on layer 3 ...");
	AppPrintService
		.printAnnotatorAgreement("Full Agreement for NegativeSentiment: " + agreementFullNegativeSentiment);
	AppPrintService.printAnnotatorAgreement(
		"Matching Agreement for NegativeSentiment: " + agreementMatchingNegativeSentiment);

	CohenKappaAgreement kappaFullImplicitAddressing = new CohenKappaAgreement(studyFullAgreementImplicitAddressing);
	CohenKappaAgreement kappaMatchingImplicitAddressing = new CohenKappaAgreement(
		studyMatchingAgreementImplicitAddressing);
	double agreementFullImplicitAddressing = kappaFullImplicitAddressing.calculateAgreement();
	double agreementMatchingImplicitAddressing = kappaMatchingImplicitAddressing.calculateAgreement();

	AppPrintService.printAnnotatorAgreement("Determination of the agreement on layer 4 ...");
	AppPrintService
		.printAnnotatorAgreement("Full Agreement for ImplicitAddressing: " + agreementFullImplicitAddressing);
	AppPrintService.printAnnotatorAgreement(
		"Matching Agreement for ImplicitAddressing: " + agreementMatchingImplicitAddressing);
	AppPrintService.printAnnotatorAgreement("");
	AppPrintService.printAnnotatorAgreement("");

	DecimalFormat df = new DecimalFormat("#.##");
	AppPrintService.printAnnotatorAgreement("Total annotations evaluated: " + annotationUnits.size());
	double percentageAgreed = ((double) countAgreedAnnotations / annotationUnits.size()) * 100;
	AppPrintService.printAnnotatorAgreement("Annotations with position match: " + countAgreedAnnotations + " ("
		+ df.format(percentageAgreed) + "%)");

	double percentageAnno1 = ((double) countDisagreedAnno1 / annotationUnits.size()) * 100;
	AppPrintService.printAnnotatorAgreement(
		"Annotations only from Annotator 1: " + countDisagreedAnno1 + " (" + df.format(percentageAnno1) + "%)");

	double percentageAnno2 = ((double) countDisagreedAnno2 / annotationUnits.size()) * 100;
	AppPrintService.printAnnotatorAgreement(
		"Annotations only from Annotator 2: " + countDisagreedAnno2 + " (" + df.format(percentageAnno2) + "%)");

    }

    private void processAnnotator1Annotations(List<AnnotationUnit> annotationUnits) {
	for (JCas anno1JCas : AnnotatorJCasService.getAnno1JCasList()) {
	    for (Sentence sentence : JCasUtil.select(anno1JCas, Sentence.class)) {
		for (Zielgruppenadressierung personAddressAnno1 : JCasUtil.subiterate(anno1JCas,
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
			annotationUnit.getAnno1Layer2Features().add(personAddressAnno1.getGroupAffiliation());
			annotationUnit.getAnno1Layer3Features().add(personAddressAnno1.getNegativeSentiment());
			annotationUnit.getAnno1Layer4Features().add(personAddressAnno1.getImplicitAddressing());
			annotationUnit.setDocumentText(sentence.getCoveredText());
			annotationUnit.setAnnotationText(personAddressAnno1.getCoveredText());
			annotationUnits.add(annotationUnit);
		    } else {
			presentAnnotation.get(0).getAnno1Layer1Features().add(personAddressAnno1.getGroupIndividual());
			presentAnnotation.get(0).getAnno1Layer2Features().add(personAddressAnno1.getGroupAffiliation());
			presentAnnotation.get(0).getAnno1Layer3Features()
				.add(personAddressAnno1.getNegativeSentiment());
			presentAnnotation.get(0).getAnno1Layer4Features()
				.add(personAddressAnno1.getImplicitAddressing());
		    }
		}
	    }
	}
    }

    private void processAnnotator2Annotations(List<AnnotationUnit> annotationUnits) {
	for (JCas anno2JCas : AnnotatorJCasService.getAnno2JCasList()) {
	    for (Sentence sentence : JCasUtil.select(anno2JCas, Sentence.class)) {
		for (Zielgruppenadressierung personAddressAnno2 : JCasUtil.subiterate(anno2JCas,
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
			annotationUnit.getAnno2Layer1Features().add(personAddressAnno2.getGroupIndividual());
			annotationUnit.getAnno2Layer2Features().add(personAddressAnno2.getGroupAffiliation());
			annotationUnit.getAnno2Layer3Features().add(personAddressAnno2.getNegativeSentiment());
			annotationUnit.getAnno2Layer4Features().add(personAddressAnno2.getImplicitAddressing());
			annotationUnit.setDocumentText(sentence.getCoveredText());
			annotationUnit.setAnnotationText(personAddressAnno2.getCoveredText());
			annotationUnits.add(annotationUnit);
		    } else {
			if (!presentAnnotation.get(0).isAnno2Present())
			    presentAnnotation.get(0).setAnno2Present(true);
			presentAnnotation.get(0).getAnno2Layer1Features().add(personAddressAnno2.getGroupIndividual());
			presentAnnotation.get(0).getAnno2Layer2Features().add(personAddressAnno2.getGroupAffiliation());
			presentAnnotation.get(0).getAnno2Layer3Features()
				.add(personAddressAnno2.getNegativeSentiment());
			presentAnnotation.get(0).getAnno2Layer4Features()
				.add(personAddressAnno2.getImplicitAddressing());
		    }
		}
	    }
	}
    }
}
