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
	System.out.println("NER TruePoisitve: " + getTruePositive());
	System.out.println("NER FalsePositive: " + getFalsePositive());
	System.out.println("NER FalseNegative: " + getFalseNegative());
	System.out.println("NER TrueNegative: " + getTrueNegative());
	System.out.println("NER Precision: " + getPrecision());
	System.out.println("NER Recall : " + getRecall());
	System.out.println("NER F1: " + getF1Score());
	System.out.println("### SentenceSentimentPrediction Results ");
    }

}
