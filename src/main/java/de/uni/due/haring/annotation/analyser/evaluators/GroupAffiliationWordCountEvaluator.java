package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import de.uni.due.haring.annotation.analyser.annotations.PersonAddress;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;
import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public class GroupAffiliationWordCountEvaluator implements AnnotationEvaluator {

    private List<SentenceAnnotation> sentenceAnnotations;

    private Multiset<String> addressAnnotationsNegative;
    private Multiset<String> addressAnnotationsGroupsNegative;
    private Multiset<String> addressAnnotationsIndividualNegative;

    private Multiset<String> addressAnnotations;
    private Multiset<String> addressAnnotationsGroups;
    private Multiset<String> addressAnnotationsIndividual;

    public GroupAffiliationWordCountEvaluator() {
	this.sentenceAnnotations = SentenceAnnotationService.getSentencesAnnotations();

	this.addressAnnotationsNegative = HashMultiset.create();
	this.addressAnnotationsGroupsNegative = HashMultiset.create();
	this.addressAnnotationsIndividualNegative = HashMultiset.create();

	this.addressAnnotations = HashMultiset.create();
	this.addressAnnotationsGroups = HashMultiset.create();
	this.addressAnnotationsIndividual = HashMultiset.create();
    }

    @Override
    public void processAnnotations() {

	sentenceAnnotations.stream().forEach(sentenceAnnotation -> {
	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment()
			    && personAddress.getGroupAffiliation().equals(GroupAffiliationType.ENVIRONMENTAL_GREENS))
		    .map(PersonAddress::getCoveredText).forEach(addressAnnotationsNegative::add);
	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment() && personAddress.isGroupAddress()
			    && personAddress.getGroupAffiliation().equals(GroupAffiliationType.ENVIRONMENTAL_GREENS))
		    .map(PersonAddress::getCoveredText).forEach(addressAnnotationsGroupsNegative::add);
	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment() && !personAddress.isGroupAddress()
			    && personAddress.getGroupAffiliation().equals(GroupAffiliationType.ENVIRONMENTAL_GREENS))
		    .map(PersonAddress::getCoveredText).forEach(addressAnnotationsIndividualNegative::add);

	    sentenceAnnotation.getPersonAddresses().stream().map(PersonAddress::getCoveredText)
		    .forEach(addressAnnotations::add);
	    sentenceAnnotation.getPersonAddresses().stream().filter(personAddress -> personAddress.isGroupAddress())
		    .map(PersonAddress::getCoveredText).forEach(addressAnnotationsGroups::add);
	    sentenceAnnotation.getPersonAddresses().stream().filter(personAddress -> !personAddress.isGroupAddress())
		    .map(PersonAddress::getCoveredText).forEach(addressAnnotationsIndividual::add);

	});
    }

    @Override
    public void printEvaluationResults() {

	ImmutableMultiset<String> addressAnnotationsSortedNegative = Multisets
		.copyHighestCountFirst(addressAnnotationsNegative);
	ImmutableMultiset<String> addressAnnotationsGroupsSortedNegative = Multisets
		.copyHighestCountFirst(addressAnnotationsGroupsNegative);
	ImmutableMultiset<String> addressAnnotationsIndividualSortedNegative = Multisets
		.copyHighestCountFirst(addressAnnotationsIndividualNegative);

	ImmutableMultiset<String> addressAnnotationsSorted = Multisets.copyHighestCountFirst(addressAnnotations);
	ImmutableMultiset<String> addressAnnotationsGroupsSorted = Multisets
		.copyHighestCountFirst(addressAnnotationsGroups);
	ImmutableMultiset<String> addressAnnotationsIndividualSorted = Multisets
		.copyHighestCountFirst(addressAnnotationsIndividual);

	AppPrintService.printSurfaceStructure("");
	AppPrintService.printSurfaceStructure("");
	AppPrintService.printSurfaceStructure("Breakdown of the annotated words ...");
	AppPrintService.printSurfaceStructure("ALL NEGATIVE: " + addressAnnotationsSortedNegative.toString());
	AppPrintService.printSurfaceStructure("GRP NEGATIVE: " + addressAnnotationsGroupsSortedNegative.toString());
	AppPrintService.printSurfaceStructure("IND NEGATIVE: " + addressAnnotationsIndividualSortedNegative.toString());

	AppPrintService.printSurfaceStructure("ALL: " + addressAnnotationsSorted.toString());
	AppPrintService.printSurfaceStructure("GRP: " + addressAnnotationsGroupsSorted.toString());
	AppPrintService.printSurfaceStructure("IND: " + addressAnnotationsIndividualSorted.toString());
    }

}
