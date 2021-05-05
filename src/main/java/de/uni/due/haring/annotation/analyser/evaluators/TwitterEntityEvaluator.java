package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Range;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
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

	    Pattern TWITTER_USER = Pattern.compile("@(?<=@)[\\w-]+");
	    Matcher m = TWITTER_USER.matcher(sentence.getCoveredText());
	    List<String> usernames = new ArrayList<>();
	    
	    while (m.find()) {
		usernames.add(m.group(0));
	    }

	}
    }

//    private Zielgruppenadressierung entityMatch(JCas jCas, Sentence sentence, String username) {
//	for (Zielgruppenadressierung personAddress : JCasUtil.subiterate(jCas, Zielgruppenadressierung.class, sentence,
//		true, true)) {
//	    if (Range.between(personAddress.getBegin() - 2, personAddress.getBegin() + 2).contains(ne.getBegin())
//		    && Range.between(personAddress.getEnd() - 2, personAddress.getEnd() + 2).contains(ne.getEnd())) {
//		return personAddress;
//	    }
//	}
//	return null;
//    }
    
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
