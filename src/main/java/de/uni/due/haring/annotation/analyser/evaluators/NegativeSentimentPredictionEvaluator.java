package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.uima.fit.util.JCasUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.PredictedSentiment;
import webanno.custom.Zielgruppenadressierung;

public class NegativeSentimentPredictionEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    public NegativeSentimentPredictionEvaluator() {
	super();
    }

    @Override
    public void processAnnotations() {
	for (Sentence sentence : JCasUtil.select(getjCas(), Sentence.class)) {
	    List<PredictedSentiment> predictedSentimentAnnotation = JCasUtil.selectCovered(getjCas(),
		    PredictedSentiment.class, sentence.getBegin(), sentence.getEnd());

	    boolean isSentimentPredictionNegative = predictedSentimentAnnotation.get(0).getNegativeSentiment();
	    for (Zielgruppenadressierung personAddress : JCasUtil.subiterate(getjCas(), Zielgruppenadressierung.class,
		    sentence, true, true)) {
		if (personAddress.getNegativeSentiment() && isSentimentPredictionNegative) {
		    increaseTruePositive();
		} else if (personAddress.getNegativeSentiment() && !isSentimentPredictionNegative) {
		    increaseFalseNegative();
		} else if (!personAddress.getNegativeSentiment() && isSentimentPredictionNegative) {
		    increaseFalsePositive();
		} else if (!personAddress.getNegativeSentiment() && !isSentimentPredictionNegative) {
		    increaseTrueNegative();
		}
	    }
	}
    }

    @Override
    public void printEvaluationResults() {
	System.out.println("### SentimentPrediction Results ");
	System.out.println("NER TruePoisitve: " + getTruePositive());
	System.out.println("NER FalsePositive: " + getFalsePositive());
	System.out.println("NER FalseNegative: " + getFalseNegative());
	System.out.println("NER TrueNegative: " + getTrueNegative());
	System.out.println("NER Precision: " + getPrecision());
	System.out.println("NER Recall : " + getRecall());
	System.out.println("NER F1: " + getF1Score());
	System.out.println("### SentimentPrediction Results ");
    }
}
