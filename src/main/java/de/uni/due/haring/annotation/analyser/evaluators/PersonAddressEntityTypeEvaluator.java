package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.TwitterUser;
import webanno.custom.Zielgruppenadressierung;

public class PersonAddressEntityTypeEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    public PersonAddressEntityTypeEvaluator() {
	super();
    }

    public PersonAddressEntityTypeEvaluator(JCas jCas) {
	super(jCas);
    }

    @Override
    public void processAnnotations() {
	for (Sentence sentence : JCasUtil.select(getjCas(), Sentence.class)) {
	    List<Zielgruppenadressierung> personAddressEntities = getPersonAddressEntitiesByPosition(sentence);

	    for (Zielgruppenadressierung personAddress : personAddressEntities) {
		List<NamedEntity> nerEntities = JCasUtil.selectCovered(NamedEntity.class, personAddress);
		List<TwitterUser> twitterUser = JCasUtil.selectCovered(TwitterUser.class, personAddress);

		if (!twitterUser.isEmpty()) {

		}

//		if (!nerEntities.isEmpty())
//		    System.out.println(nerEntities.get(0));
	    }

	    setFalseNegative(getFalseNegative() + personAddressEntities.size());
	}

    }

    @Override
    public void printEvaluationResults() {

    }

}
