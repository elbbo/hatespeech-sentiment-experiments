package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Range;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
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
		    }
		    increaseTruePositive();
		} else {
		    increaseFalsePositive();
		}
	    }
	    setFalseNegative(getFalseNegative() + personAddressEntities.size());

	}
    }

    private Zielgruppenadressierung entityMatch(JCas jCas, Sentence sentence, TwitterUser twitterUser) {
	for (Zielgruppenadressierung personAddress : JCasUtil.subiterate(jCas, Zielgruppenadressierung.class, sentence, true, true)) {
	    if (Range.between(personAddress.getBegin() - 2, personAddress.getBegin() + 2).contains(twitterUser.getBegin())
		    && Range.between(personAddress.getEnd() - 2, personAddress.getEnd() + 2).contains(twitterUser.getEnd())) {
		return personAddress;
	    }
	}
	return null;
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
