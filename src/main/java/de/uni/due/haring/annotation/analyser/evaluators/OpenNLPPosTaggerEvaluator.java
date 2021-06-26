package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import webanno.custom.Zielgruppenadressierung;

public class OpenNLPPosTaggerEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    public OpenNLPPosTaggerEvaluator() {
	super();
    }

    public OpenNLPPosTaggerEvaluator(JCas jCas) {
	super(jCas);
    }

    @Override
    public void processAnnotations() {
	for (Sentence sentence : JCasUtil.select(getjCas(), Sentence.class)) {
	    List<Zielgruppenadressierung> personAddressEntities = getPersonAddressEntitiesByPosition(sentence);
	    for (POS tagClass : JCasUtil.selectCovered(getjCas(), POS.class, sentence)) {
		if (tagClass.getPosValue().equals("NN")) {
		    Zielgruppenadressierung entity = entityMatch(getjCas(), sentence, tagClass);
		    if (ObjectUtils.isNotEmpty(entity)) {
			if (personAddressEntities.contains(entity)) {
			    personAddressEntities.remove(entity);
			    increaseTruePositive();
			}
		    } else {
			increaseFalsePositive();
		    }
		}
	    }
	    setFalseNegative(getFalseNegative() + personAddressEntities.size());
	}

    }

    @Override
    public void printEvaluationResults() {
	AppPrintService.printAutomatedDetection("Results of the Part of Speech Experiment ...");
	AppPrintService.printAutomatedDetection("PoS TruePoisitve: " + getTruePositive());
	AppPrintService.printAutomatedDetection("PoS FalsePositive: " + getFalsePositive());
	AppPrintService.printAutomatedDetection("PoS FalseNegative: " + getFalseNegative());
	AppPrintService.printAutomatedDetection("PoS Precision: " + getPrecision());
	AppPrintService.printAutomatedDetection("PoS Recall : " + getRecall());
	AppPrintService.printAutomatedDetection("PoS F1: " + getF1Score());
    }

}
