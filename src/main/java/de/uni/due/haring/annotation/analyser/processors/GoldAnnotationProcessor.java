package de.uni.due.haring.annotation.analyser.processors;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.analyser.annotations.PersonAddress;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;
import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;
import webanno.custom.Zielgruppenadressierung;

public class GoldAnnotationProcessor extends JCasAnnotator_ImplBase {

    public static final String PARAM_ANNOTATION_DATA_INPUT_FILE_PATH = "AnnotationDataInputFilePath";
    @ConfigurationParameter(name = PARAM_ANNOTATION_DATA_INPUT_FILE_PATH, mandatory = true)
    private String annotationDataInputFilePath;

    private static List<SentenceAnnotation> sentencesAnnotations = new ArrayList<>();
    private Map<String, String> germEvalAnnotations;

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
	super.initialize(context);
	germEvalAnnotations = new HashMap<String, String>();

	try {
	    BufferedReader reader = new BufferedReader(
		    new InputStreamReader(new FileInputStream(annotationDataInputFilePath), "UTF-8"));
	    reader.lines().forEach(line -> {
		String[] splittedLine = line.split("\t");
		germEvalAnnotations.put(splittedLine[0], splittedLine[1]);
	    });
	    reader.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
	for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
	    SentenceAnnotation sentenceAnnotation = new SentenceAnnotation();
	    sentenceAnnotation.setDocumentText(sentence.getCoveredText());
	    sentenceAnnotation.setPersonAddresses(new ArrayList<>());
	    sentenceAnnotation.setOffenseGoldAnnotation(germEvalAnnotations.get(sentence.getCoveredText()));
	    sentenceAnnotation.setOffensive(germEvalAnnotations.get(sentence.getCoveredText()).equals("OFFENSE"));

	    for (Zielgruppenadressierung pa : JCasUtil.subiterate(aJCas, Zielgruppenadressierung.class, sentence, true,
		    true)) {
		PersonAddress personAddress = new PersonAddress();
		personAddress.setCoveredText(pa.getCoveredText());
		personAddress.setAddressType(pa.getGroupIndividual());
		personAddress.setGroupAddress(pa.getGroupIndividual().equals("Group"));
		personAddress.setGroupAffiliation(GroupAffiliationType.valueOfLabel(pa.getGroupAffiliation()));
		personAddress.setHasNegativeSentiment(pa.getNegativeSentiment());
		personAddress.setImplicit(pa.getMarkImplicit());

		sentenceAnnotation.getPersonAddresses().add(personAddress);
	    }
	    sentenceAnnotation.setHasNegativeSentiment(!sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment()).collect(Collectors.toList())
		    .isEmpty());

	    sentencesAnnotations.add(sentenceAnnotation);
	}

	SentenceAnnotationService.setSentencesAnnotations(sentencesAnnotations);
    }
}
