package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.TwitterUser;
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
	System.out.println("### TwitterEntity Results ");
	System.out.println("NER TruePoisitve: " + getTruePositive());
	System.out.println("NER FalsePositive: " + getFalsePositive());
	System.out.println("NER FalseNegative: " + getFalseNegative());
	System.out.println("NER Precision: " + getPrecision());
	System.out.println("NER Recall : " + getRecall());
	System.out.println("NER F1: " + getF1Score());
	System.out.println("### TwitterEntity Results ");
    }
}
