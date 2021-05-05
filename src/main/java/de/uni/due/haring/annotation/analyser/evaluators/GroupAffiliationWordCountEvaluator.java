package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import de.uni.due.haring.annotation.analyser.annotations.PersonAddress;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;

public class GroupAffiliationWordCountEvaluator implements AnnotationEvaluator {

    private List<SentenceAnnotation> sentenceAnnotations;

    private Multiset<String> addressAnnotations;
    private Multiset<String> addressAnnotationsGroups;
    private Multiset<String> addressAnnotationsIndividual;

    public GroupAffiliationWordCountEvaluator() {
	this.sentenceAnnotations = SentenceAnnotationService.getSentencesAnnotations();

	this.addressAnnotations = HashMultiset.create();
	this.addressAnnotationsGroups = HashMultiset.create();
	this.addressAnnotationsIndividual = HashMultiset.create();
    }

    @Override
    public void processAnnotations() {

	sentenceAnnotations.stream().forEach(sentenceAnnotation -> {
	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment()).map(PersonAddress::getCoveredText)
		    .forEach(addressAnnotations::add);
	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment() && personAddress.isGroupAddress())
		    .map(PersonAddress::getCoveredText).forEach(addressAnnotationsGroups::add);
	    sentenceAnnotation.getPersonAddresses().stream()
		    .filter(personAddress -> personAddress.hasNegativeSentiment() && !personAddress.isGroupAddress())
		    .map(PersonAddress::getCoveredText).forEach(addressAnnotationsIndividual::add);

	});
    }

    @Override
    public void printEvaluationResults() {

	ImmutableMultiset<String> addressAnnotationsSorted = Multisets.copyHighestCountFirst(addressAnnotations);
	ImmutableMultiset<String> addressAnnotationsGroupsSorted = Multisets
		.copyHighestCountFirst(addressAnnotationsGroups);
	ImmutableMultiset<String> addressAnnotationsIndividualSorted = Multisets
		.copyHighestCountFirst(addressAnnotationsIndividual);

	System.out.println("### Counts");
	System.out.println("ALL: " + addressAnnotationsSorted.toString());
	System.out.println("GRP: " + addressAnnotationsGroupsSorted.toString());
	System.out.println("IND: " + addressAnnotationsIndividualSorted.toString());
    }

}
