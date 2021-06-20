package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
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
	System.out.println("### POS Results ");
	System.out.println("PoS TruePoisitve: " + getTruePositive());
	System.out.println("PoS FalsePositive: " + getFalsePositive());
	System.out.println("PoS FalseNegative: " + getFalseNegative());
	System.out.println("PoS Precision: " + getPrecision());
	System.out.println("PoS Recall : " + getRecall());
	System.out.println("PoS F1: " + getF1Score());
	System.out.println("### POS Results ");
    }

}
