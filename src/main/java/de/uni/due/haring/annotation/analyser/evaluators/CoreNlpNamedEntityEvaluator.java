package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import webanno.custom.Zielgruppenadressierung;

public class CoreNlpNamedEntityEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    public CoreNlpNamedEntityEvaluator() {
	super();
    }

    public CoreNlpNamedEntityEvaluator(JCas jCas) {
	super(jCas);
    }

    @Override
    public void processAnnotations() {
	for (Sentence sentence : JCasUtil.select(getjCas(), Sentence.class)) {
	    List<Zielgruppenadressierung> personAddressEntities = getPersonAddressEntitiesByPosition(sentence);
	    for (NamedEntity ne : JCasUtil.selectCovered(getjCas(), NamedEntity.class, sentence)) {
		Zielgruppenadressierung entity = entityMatch(getjCas(), sentence, ne);
		if (ObjectUtils.isNotEmpty(entity)) {
		    if (personAddressEntities.contains(entity)) {
			personAddressEntities.remove(entity);
			increaseTruePositive();
		    }
		} else {
		    increaseFalsePositive();
		}
	    }
	    setFalseNegative(getFalseNegative() + personAddressEntities.size());
	}

    }

    private boolean exactEntityMatch(SentenceAnnotation goldSentenceAnnotation, NamedEntity ne) {
	return goldSentenceAnnotation.getPersonAddresses().stream()
		.anyMatch(pa -> pa.getCoveredText().equals(ne.getCoveredText()));
    }

    @Override
    public void printEvaluationResults() {
	AppPrintService.printAutomatedDetection("Results of the Named Entity Recognition Experiment ...");
	AppPrintService.printAutomatedDetection("NER TruePoisitve: " + getTruePositive());
	AppPrintService.printAutomatedDetection("NER FalsePositive: " + getFalsePositive());
	AppPrintService.printAutomatedDetection("NER FalseNegative: " + getFalseNegative());
	AppPrintService.printAutomatedDetection("NER Precision: " + getPrecision());
	AppPrintService.printAutomatedDetection("NER Recall : " + getRecall());
	AppPrintService.printAutomatedDetection("NER F1: " + getF1Score());

    }
}
