package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.TwitterUser;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import webanno.custom.Zielgruppenadressierung;

public class TwitterEntityEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    public TwitterEntityEvaluator() {
	super();
    }

    public TwitterEntityEvaluator(JCas jCas) {
	super(jCas);
    }

    @Override
    public void processAnnotations() {
	for (Sentence sentence : JCasUtil.select(getjCas(), Sentence.class)) {
	    List<Zielgruppenadressierung> personAddressEntities = getPersonAddressEntitiesByPosition(sentence);
	    for (TwitterUser twitterUser : JCasUtil.selectCovered(getjCas(), TwitterUser.class, sentence)) {
		Zielgruppenadressierung entity = entityMatch(getjCas(), sentence, twitterUser);
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

    @Override
    public void printEvaluationResults() {
	AppPrintService.printAutomatedDetection("Results of the Twitter user Experiment ...");
	AppPrintService.printAutomatedDetection("TW TruePoisitve: " + getTruePositive());
	AppPrintService.printAutomatedDetection("TW FalsePositive: " + getFalsePositive());
	AppPrintService.printAutomatedDetection("TW FalseNegative: " + getFalseNegative());
	AppPrintService.printAutomatedDetection("TW Precision: " + getPrecision());
	AppPrintService.printAutomatedDetection("TW Recall : " + getRecall());
	AppPrintService.printAutomatedDetection("TW F1: " + getF1Score());
    }
}
