package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;
import java.util.stream.Collectors;

import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;
import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public class PersonAddressEvaluator implements AnnotationEvaluator {

    private List<SentenceAnnotation> sentenceAnnotations;

    private int totalSentences;
    private int totalPersonAddresses;
    private int totalGroups;
    private int totalIndividuals;
    private int totalIndividualsAddressThroughTwitter;
    private int totalIndividualsAddressThroughTwitterWithNegativeSentiment;

    private int totalPersonAddressesWithNegativeSentiment;
    private int totalGroupsWithNegativeSentiment;
    private int totalIndividualsWithNegativeSentiment;

    private float averagePersonAddressesPerSentence;
    private float averageGroupsPerSentence;
    private float averageIndividualsPerSentence;

    public PersonAddressEvaluator() {
	this.sentenceAnnotations = SentenceAnnotationService.getSentencesAnnotations();
    }

    @Override
    public void processAnnotations() {
	totalSentences = getTotalSentences();
	totalPersonAddresses = getTotalPersonAddress();
	totalGroups = getTotalPersonAdressGroups();
	totalIndividuals = getTotalPersonAdressIndividuals();
	totalPersonAddressesWithNegativeSentiment = getTotalPersonAddressWithNegativeSentiment();
	totalGroupsWithNegativeSentiment = getTotalPersonAdressGroupsWithNegativeSentiment();
	totalIndividualsWithNegativeSentiment = getTotalPersonAdressIndividualsWithNegativeSentiment();
	totalIndividualsAddressThroughTwitterWithNegativeSentiment = getTotalIndividualsAddressThroughTwitterWithNegativeSentiment();

	averagePersonAddressesPerSentence = getAveragePersonAddressesPerSentence();
	averageGroupsPerSentence = getAverageGroupsPerSentence();
	averageIndividualsPerSentence = getAverageIndividualsPerSentence();
	totalIndividualsAddressThroughTwitter = getTotalIndividualsAddressThroughTwitter();

    }

    @Override
    public void printEvaluationResults() {

	AppPrintService.printSurfaceStructure("Number of annotated tweets: " + totalSentences);
	AppPrintService.printSurfaceStructure("Number of group and person addresses: " + totalPersonAddresses);
	AppPrintService.printSurfaceStructure("Number of group and person addresses w/negativeSentiment: " + totalPersonAddressesWithNegativeSentiment);
	AppPrintService.printSurfaceStructure("Number of group and person addresses average p/s: " + averagePersonAddressesPerSentence);

	AppPrintService.printSurfaceStructure("Number of group addresses: " + totalGroups);
	AppPrintService.printSurfaceStructure("Number of group addresses w/negativeSentiment: " + totalGroupsWithNegativeSentiment);
	AppPrintService.printSurfaceStructure("Number of group addresses average p/s: " + averageGroupsPerSentence);
	
	AppPrintService.printSurfaceStructure("Number of person addresses: " + totalIndividuals);
	AppPrintService.printSurfaceStructure("Number of person addresses w/negativeSentiment: " + totalIndividualsWithNegativeSentiment);
	AppPrintService.printSurfaceStructure("Number of person addresses average p/s: " + averageIndividualsPerSentence);
	
	AppPrintService.printSurfaceStructure("Number of individuals addressed through twitter: " + totalIndividualsAddressThroughTwitter);
	AppPrintService.printSurfaceStructure("Number of individuals addressed through twitter w/negativeSentiment: " + totalIndividualsAddressThroughTwitterWithNegativeSentiment);

    }

    private int getTotalPersonAdressIndividualsWithNegativeSentiment() {
	return sentenceAnnotations.stream()
		.mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses().stream()
			.filter(pa -> !pa.isGroupAddress() && pa.hasNegativeSentiment()).collect(Collectors.toList())
			.size())
		.sum();
    }

    private int getTotalPersonAdressGroupsWithNegativeSentiment() {
	return sentenceAnnotations.stream()
		.mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses().stream()
			.filter(pa -> pa.isGroupAddress() && pa.hasNegativeSentiment()).collect(Collectors.toList())
			.size())
		.sum();
    }

    private int getTotalPersonAddressWithNegativeSentiment() {
	return sentenceAnnotations.stream().mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses()
		.stream().filter(pa -> pa.hasNegativeSentiment()).collect(Collectors.toList()).size()).sum();
    }

    private int getTotalPersonAdressIndividuals() {
	return sentenceAnnotations.stream().mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses()
		.stream().filter(pa -> !pa.isGroupAddress()).collect(Collectors.toList()).size()).sum();
    }

    private int getTotalPersonAdressGroups() {
	return sentenceAnnotations.stream().mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses()
		.stream().filter(pa -> pa.isGroupAddress()).collect(Collectors.toList()).size()).sum();
    }

    private int getTotalPersonAddress() {
	return sentenceAnnotations.stream()
		.mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses().size()).sum();
    }

    private int getTotalIndividualsAddressThroughTwitter() {
	return sentenceAnnotations.stream()
		.mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses().stream()
			.filter(pa -> pa.getGroupAffiliation().label.equals(GroupAffiliationType.OTHER.label)
				&& pa.getAddressType().equals("Individual") && pa.getCoveredText().startsWith("@"))
			.collect(Collectors.toList()).size())
		.sum();
    }

    private int getTotalIndividualsAddressThroughTwitterWithNegativeSentiment() {
	return sentenceAnnotations.stream()
		.mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses().stream()
			.filter(pa -> pa.getGroupAffiliation().label.equals(GroupAffiliationType.OTHER.label)
				&& pa.getAddressType().equals("Individual") && pa.getCoveredText().startsWith("@")
				&& pa.hasNegativeSentiment())
			.collect(Collectors.toList()).size())
		.sum();
    }

    private int getTotalSentences() {
	return sentenceAnnotations.size();
    }

    private float getAverageIndividualsPerSentence() {
	return (float) totalIndividuals / totalSentences;
    }

    private float getAverageGroupsPerSentence() {
	return (float) totalGroups / totalSentences;
    }

    private float getAveragePersonAddressesPerSentence() {
	return (float) totalPersonAddresses / totalSentences;
    }

}
