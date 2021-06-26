package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.uima.fit.util.JCasUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.PredictedSentiment;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
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
	AppPrintService.printAutomatedDetection("Results of the experiments on layer 3 (Annotation Level) ");
	AppPrintService.printAutomatedDetection("TruePoisitve: " + getTruePositive());
	AppPrintService.printAutomatedDetection("FalsePositive: " + getFalsePositive());
	AppPrintService.printAutomatedDetection("FalseNegative: " + getFalseNegative());
	AppPrintService.printAutomatedDetection("TrueNegative: " + getTrueNegative());
	AppPrintService.printAutomatedDetection("Precision: " + getPrecision());
	AppPrintService.printAutomatedDetection("Recall : " + getRecall());
	AppPrintService.printAutomatedDetection("F1: " + getF1Score());
    }
}
