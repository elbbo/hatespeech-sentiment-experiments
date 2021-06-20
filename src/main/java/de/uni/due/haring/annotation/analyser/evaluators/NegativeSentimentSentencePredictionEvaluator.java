package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.uima.fit.util.JCasUtil;

import de.uni.due.haring.annotation.PredictedSentiment;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
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
	System.out.println("### SentenceSentimentPrediction Results ");
	System.out.println("SEN TruePoisitve: " + getTruePositive());
	System.out.println("SEN FalsePositive: " + getFalsePositive());
	System.out.println("SEN FalseNegative: " + getFalseNegative());
	System.out.println("SEN TrueNegative: " + getTrueNegative());
	System.out.println("SEN Precision: " + getPrecision());
	System.out.println("SEN Recall : " + getRecall());
	System.out.println("SEN F1: " + getF1Score());
	System.out.println("### SentenceSentimentPrediction Results ");
    }

}
