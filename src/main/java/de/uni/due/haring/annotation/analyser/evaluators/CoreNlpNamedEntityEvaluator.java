package de.uni.due.haring.annotation.analyser.evaluators;

import org.apache.commons.lang3.Range;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;
import webanno.custom.Zielgruppenadressierung;

public class CoreNlpNamedEntityEvaluator implements AnnotationEvaluator {

    private JCas jCas;

    public CoreNlpNamedEntityEvaluator(JCas jCas) {
	this.jCas = jCas;
    }

    @Override
    public void processAnnotations() {

	int index = 0;
	for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
	    SentenceAnnotation goldSentenceAnnotation = SentenceAnnotationService.getSentencesAnnotations().get(index);

	    System.out.println("###");
	    System.out.println("Sentence: " + sentence.getCoveredText());
	    StringBuilder personAddresses = new StringBuilder();
	    personAddresses.append("PersonAddresses: ");
	    goldSentenceAnnotation.getPersonAddresses().stream()
		    .forEach(pa -> personAddresses.append(pa.getCoveredText() + ", "));
	    System.out.println(personAddresses.toString());

	    StringBuilder foundEntities = new StringBuilder();
	    foundEntities.append("Found Entities: ");
	    int entitiesHit = 0;
	    for (NamedEntity ne : JCasUtil.selectCovered(jCas, NamedEntity.class, sentence)) {
		foundEntities.append(ne.getCoveredText() + ", ");

		if (entityMatch(jCas, sentence, ne)) {
		    entitiesHit++;
		}
	    }
	    System.out.println(foundEntities.toString());
	    System.out.println("Hits: " + entitiesHit + " from: " + goldSentenceAnnotation.getPersonAddresses().size());
	    System.out.println("###");
	    index++;
	}
    }

    private boolean entityMatch(JCas jCas, Sentence sentence, NamedEntity ne) {
	boolean entityMatch = false;
	for (Zielgruppenadressierung personAddress : JCasUtil.subiterate(jCas, Zielgruppenadressierung.class, sentence,
		true, true)) {
	    if (Range.between(personAddress.getBegin() - 2, personAddress.getBegin() + 2).contains(ne.getBegin())
		    && Range.between(personAddress.getEnd() - 2, personAddress.getEnd() + 2).contains(ne.getEnd())) {
		entityMatch = true;
	    }
	}
	return entityMatch;

    }

    private boolean exactEntityMatch(SentenceAnnotation goldSentenceAnnotation, NamedEntity ne) {
	return goldSentenceAnnotation.getPersonAddresses().stream()
		.anyMatch(pa -> pa.getCoveredText().equals(ne.getCoveredText()));
    }

    @Override
    public void printEvaluationResults() {
	// TODO Auto-generated method stub
    }
}
