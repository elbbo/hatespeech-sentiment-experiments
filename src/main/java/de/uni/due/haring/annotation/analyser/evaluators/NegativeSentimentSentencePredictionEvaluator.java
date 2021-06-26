package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.uima.fit.util.JCasUtil;

import de.uni.due.haring.annotation.PredictedSentiment;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;

public class NegativeSentimentSentencePredictionEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    private List<SentenceAnnotation> sentenceAnnotations;

    public NegativeSentimentSentencePredictionEvaluator() {
	super();
    }

    @Override
    public void processAnnotations() {
	this.sentenceAnnotations = SentenceAnnotationService.getSentencesAnnotations();
	for (PredictedSentiment prediction : JCasUtil.select(getjCas(), PredictedSentiment.class)) {
	    SentenceAnnotation goldSentence = sentenceAnnotations.stream()
		    .filter(sentence -> sentence.getDocumentText().equals(prediction.getCoveredText())).findFirst()
		    .get();
	    if (goldSentence.hasNegativeSentiment() && prediction.getNegativeSentiment()) {
		increaseTruePositive();
	    } else if (goldSentence.hasNegativeSentiment() && !prediction.getNegativeSentiment()) {
		increaseFalseNegative();
	    } else if (!goldSentence.hasNegativeSentiment() && prediction.getNegativeSentiment()) {
		increaseFalsePositive();
	    } else if (!goldSentence.hasNegativeSentiment() && !prediction.getNegativeSentiment()) {
		increaseTrueNegative();
	    }
	}
    }

    @Override
    public void printEvaluationResults() {
	AppPrintService.printAutomatedDetection("");
	AppPrintService.printAutomatedDetection("");
	AppPrintService.printAutomatedDetection("Results of the experiments on layer 3 (Sentence Level) ");
	AppPrintService.printAutomatedDetection("TruePoisitve: " + getTruePositive());
	AppPrintService.printAutomatedDetection("FalsePositive: " + getFalsePositive());
	AppPrintService.printAutomatedDetection("FalseNegative: " + getFalseNegative());
	AppPrintService.printAutomatedDetection("TrueNegative: " + getTrueNegative());
	AppPrintService.printAutomatedDetection("Precision: " + getPrecision());
	AppPrintService.printAutomatedDetection("Recall : " + getRecall());
	AppPrintService.printAutomatedDetection("F1: " + getF1Score());
    }

}
