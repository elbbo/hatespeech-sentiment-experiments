package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Range;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import webanno.custom.Zielgruppenadressierung;

public class CoreNlpNamedEntityEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    public CoreNlpNamedEntityEvaluator() {
	super();
    }

    public CoreNlpNamedEntityEvaluator(JCas jCas) {
	super(jCas);
    }

    @Override
    public void processAnnotations() {
	for (Sentence sentence : JCasUtil.select(getjCas(), Sentence.class)) {
	    List<Zielgruppenadressierung> personAddressEntities = getPersonAddressEntitiesByPosition(sentence);
	    for (NamedEntity ne : JCasUtil.selectCovered(getjCas(), NamedEntity.class, sentence)) {
		Zielgruppenadressierung entity = entityMatch(getjCas(), sentence, ne);
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

    private Zielgruppenadressierung entityMatch(JCas jCas, Sentence sentence, NamedEntity ne) {
	for (Zielgruppenadressierung personAddress : JCasUtil.subiterate(jCas, Zielgruppenadressierung.class, sentence,
		true, true)) {
	    if (Range.between(personAddress.getBegin() - 2, personAddress.getBegin() + 2).contains(ne.getBegin())
		    && Range.between(personAddress.getEnd() - 2, personAddress.getEnd() + 2).contains(ne.getEnd())) {
		return personAddress;
	    }
	}
	return null;

    }

    private boolean exactEntityMatch(SentenceAnnotation goldSentenceAnnotation, NamedEntity ne) {
	return goldSentenceAnnotation.getPersonAddresses().stream()
		.anyMatch(pa -> pa.getCoveredText().equals(ne.getCoveredText()));
    }

    @Override
    public void printEvaluationResults() {
	System.out.println("### NER Results ");
	System.out.println("NER TruePoisitve: " + getTruePositive());
	System.out.println("NER FalsePositive: " + getFalsePositive());
	System.out.println("NER FalseNegative: " + getFalseNegative());
	System.out.println("NER Precision: " + getPrecision());
	System.out.println("NER Recall : " + getRecall());
	System.out.println("NER F1: " + getF1Score());
	System.out.println("### NER Results ");
    }
}
