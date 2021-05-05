package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import com.google.common.collect.EnumMultiset;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import de.uni.due.haring.annotation.analyser.annotations.PersonAddress;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;
import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public class GroupAffiliationEvaluator implements AnnotationEvaluator {

    private List<SentenceAnnotation> sentenceAnnotations;
    private EnumMultiset<GroupAffiliationType> groupAffiliationSet;
    private EnumMultiset<GroupAffiliationType> groupAffiliationWithNegativeSentimentSet;

    private EnumMultiset<GroupAffiliationType> individualsGroupAffiliationSet;
    private EnumMultiset<GroupAffiliationType> individualsGroupAffiliationWithNegativeSentimentSet;

    private EnumMultiset<GroupAffiliationType> groupsGroupAffiliationSet;
    private EnumMultiset<GroupAffiliationType> groupsGroupAffiliationWithNegativeSentimentSet;

    public GroupAffiliationEvaluator() {
	this.sentenceAnnotations = SentenceAnnotationService.getSentencesAnnotations();
	this.groupAffiliationSet = EnumMultiset.create(GroupAffiliationType.class);
	this.groupAffiliationWithNegativeSentimentSet = EnumMultiset.create(GroupAffiliationType.class);

	this.individualsGroupAffiliationSet = EnumMultiset.create(GroupAffiliationType.class);
	this.individualsGroupAffiliationWithNegativeSentimentSet = EnumMultiset.create(GroupAffiliationType.class);

	this.groupsGroupAffiliationSet = EnumMultiset.create(GroupAffiliationType.class);
	this.groupsGroupAffiliationWithNegativeSentimentSet = EnumMultiset.create(GroupAffiliationType.class);

    }

    @Override
    public void processAnnotations() {

	sentenceAnnotations.stream().forEach(sentenceAnnotation -> {
	    sentenceAnnotation.getPersonAddresses().stream().map(PersonAddress::getGroupAffiliation)
		    .forEach(groupAffiliationSet::add);
	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment())
		    .map(PersonAddress::getGroupAffiliation).forEach(groupAffiliationWithNegativeSentimentSet::add);

	    sentenceAnnotation.getPersonAddresses().stream().filter(personAddress -> personAddress.isGroupAddress())
		    .map(PersonAddress::getGroupAffiliation).forEach(groupsGroupAffiliationSet::add);

	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.isGroupAddress() && personAddress.hasNegativeSentiment())
		    .map(PersonAddress::getGroupAffiliation)
		    .forEach(groupsGroupAffiliationWithNegativeSentimentSet::add);

	    sentenceAnnotation.getPersonAddresses().stream().filter(personAddress -> !personAddress.isGroupAddress())
		    .map(PersonAddress::getGroupAffiliation).forEach(individualsGroupAffiliationSet::add);

	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> !personAddress.isGroupAddress() && personAddress.hasNegativeSentiment())
		    .map(PersonAddress::getGroupAffiliation)
		    .forEach(individualsGroupAffiliationWithNegativeSentimentSet::add);
	});
    }

    @Override
    public void printEvaluationResults() {
	System.out.println("Targeted Groups: " + groupAffiliationSet.toString());
	System.out.println("Targeted Groups w/negSentiment: " + groupAffiliationWithNegativeSentimentSet.toString());

	System.out.println("Group address - Targeted Groups: " + groupsGroupAffiliationSet.toString());
	System.out.println("Group address - Targeted Groups w/negSentiment: "
		+ groupsGroupAffiliationWithNegativeSentimentSet.toString());

	System.out.println("Individual address - Targeted Groups: " + individualsGroupAffiliationSet.toString());
	System.out.println("Individual address - Targeted Groups w/negSentiment: "
		+ individualsGroupAffiliationWithNegativeSentimentSet.toString());

	float sumTotal = groupAffiliationSet.entrySet().stream().mapToInt(i -> i.getCount()).sum();
	System.out.println("###");
	System.out.println("###");
	System.out.println("###");
	System.out.println("Total: " + sumTotal);
	groupAffiliationSet.entrySet().stream().forEach(entry -> {
	    System.out.println(entry.getElement().label + ": " + entry.getCount() + " ("
		    + (entry.getCount() / sumTotal) * 100 + ")");
	});
	System.out.println("###");
	System.out.println("###");
	System.out.println("###");

    }

    public EnumMultiset<GroupAffiliationType> getGroupAffiliationSet() {
	return groupAffiliationSet;
    }

    public EnumMultiset<GroupAffiliationType> getGroupAffiliationWithNegativeSentimentSet() {
	return groupAffiliationWithNegativeSentimentSet;
    }

}
