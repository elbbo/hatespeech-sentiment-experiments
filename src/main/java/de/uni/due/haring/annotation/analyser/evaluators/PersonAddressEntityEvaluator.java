package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.TwitterUser;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import webanno.custom.Zielgruppenadressierung;

public class PersonAddressEntityEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    public PersonAddressEntityEvaluator() {
	super();
    }

    public PersonAddressEntityEvaluator(JCas jCas) {
	super(jCas);
    }

    @Override
    public void processAnnotations() {
	for (Sentence sentence : JCasUtil.select(getjCas(), Sentence.class)) {
	    List<Zielgruppenadressierung> personAddressEntities = getPersonAddressEntitiesByPosition(sentence);

	    List<Annotation> annotations = new ArrayList<>();
	    List<Annotation> twitterUser = new ArrayList<>(
		    JCasUtil.selectCovered(getjCas(), TwitterUser.class, sentence));
	    List<Annotation> nerEntities = new ArrayList<>(
		    JCasUtil.selectCovered(getjCas(), NamedEntity.class, sentence));
	    List<POS> posList = JCasUtil.selectCovered(getjCas(), POS.class, sentence).stream()
		    .filter(tagClass -> tagClass.getPosValue().equals("NN")).collect(Collectors.toList());
	    List<Annotation> posEntities = new ArrayList<>(posList);

	    annotations.addAll(twitterUser);
	    annotations.addAll(nerEntities);
	    annotations.addAll(posEntities);

	    for (Annotation annotation : annotations) {
		Zielgruppenadressierung entity = entityMatch(getjCas(), sentence, annotation);
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
	AppPrintService.printAutomatedDetection("Results of the Combined Approach on Layer 1 ...");
	AppPrintService.printAutomatedDetection("CA TruePoisitve: " + getTruePositive());
	AppPrintService.printAutomatedDetection("CA FalsePositive: " + getFalsePositive());
	AppPrintService.printAutomatedDetection("CA FalseNegative: " + getFalseNegative());
	AppPrintService.printAutomatedDetection("CA Precision: " + getPrecision());
	AppPrintService.printAutomatedDetection("CA Recall : " + getRecall());
	AppPrintService.printAutomatedDetection("CA F1: " + getF1Score());
    }

}
