package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import com.google.common.collect.EnumMultiset;

import de.uni.due.haring.annotation.analyser.annotations.PersonAddress;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;
import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public class GroupAffiliationEvaluator implements AnnotationEvaluator {

    private List<SentenceAnnotation> sentenceAnnotations;
    private EnumMultiset<GroupAffiliationType> groupAffiliationSet;
    private EnumMultiset<GroupAffiliationType> groupAffiliationWithNegativeSentimentSet;

    public GroupAffiliationEvaluator() {
	this.sentenceAnnotations = SentenceAnnotationService.getSentencesAnnotations();
	this.groupAffiliationSet = EnumMultiset.create(GroupAffiliationType.class);
	this.groupAffiliationWithNegativeSentimentSet = EnumMultiset.create(GroupAffiliationType.class);
    }

    @Override
    public void processAnnotations() {
	sentenceAnnotations.stream().forEach(sentenceAnnotation -> {
	    sentenceAnnotation.getPersonAddresses().stream().map(PersonAddress::getGroupAffiliation)
		    .forEach(groupAffiliationSet::add);
	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment())
		    .map(PersonAddress::getGroupAffiliation).forEach(groupAffiliationWithNegativeSentimentSet::add);
	});
    }

    @Override
    public void printEvaluationResults() {
	System.out.println("Targeted Groups: " + groupAffiliationSet.toString());
	System.out.println("Targeted Groups w/negSentiment: " + groupAffiliationWithNegativeSentimentSet.toString());
    }

    public EnumMultiset<GroupAffiliationType> getGroupAffiliationSet() {
	return groupAffiliationSet;
    }

    public EnumMultiset<GroupAffiliationType> getGroupAffiliationWithNegativeSentimentSet() {
	return groupAffiliationWithNegativeSentimentSet;
    }

}
