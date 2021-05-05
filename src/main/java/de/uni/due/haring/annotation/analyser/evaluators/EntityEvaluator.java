package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import webanno.custom.Zielgruppenadressierung;

public class EntityEvaluator {

    private JCas jCas;

    private int truePositive = 0;
    private int falsePositive = 0;
    private int falseNegative = 0;

    public EntityEvaluator() {
    }

    public EntityEvaluator(JCas jCas) {
	this.jCas = jCas;
    }

    public List<Zielgruppenadressierung> getPersonAddressEntitiesByPosition(Sentence sentence) {
	List<Zielgruppenadressierung> personAddressEntities = new ArrayList<>();
	for (Zielgruppenadressierung personAddress : JCasUtil.subiterate(jCas, Zielgruppenadressierung.class,
		sentence, true, true)) {
	    boolean skipAddress = false;
	    for (Zielgruppenadressierung za : personAddressEntities) {
		if (personAddress.getBegin() == za.getBegin() && personAddress.getEnd() == za.getEnd()
			|| personAddress.getImplicitAddressing()) {
		    skipAddress = true;
		}
	    }
	    if (!skipAddress)
		personAddressEntities.add(personAddress);
	}

	return personAddressEntities;
    }

    public int getTruePositive() {
	return truePositive;
    }

    public void setTruePositive(int truePositive) {
	this.truePositive = truePositive;
    }

    public void increaseTruePositive() {
	this.truePositive++;
    }

    public int getFalsePositive() {
	return falsePositive;
    }

    public void setFalsePositive(int falsePositive) {
	this.falsePositive = falsePositive;
    }

    public void increaseFalsePositive() {
	this.falsePositive++;
    }

    public int getFalseNegative() {
	return falseNegative;
    }

    public void setFalseNegative(int falseNegative) {
	this.falseNegative = falseNegative;
    }

    public void increaseFalseNegative() {
	this.falseNegative++;
    }

    public float getPrecision() {
	return (float) truePositive / (truePositive + falsePositive);
    }
    
    public float getRecall() {
	return (float) truePositive / (truePositive + falseNegative);
    }
    
    public float getF1Score() {
	return 2 * ((getPrecision() * getRecall()) / (getPrecision() + getRecall()));
    }
    
    public void setjCas(JCas jCas) {
	this.jCas = jCas;
    }

    public JCas getjCas() {
	return jCas;
    }

}
