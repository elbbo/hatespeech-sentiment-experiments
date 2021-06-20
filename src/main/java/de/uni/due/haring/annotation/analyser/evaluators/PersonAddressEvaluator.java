package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;
import java.util.stream.Collectors;

import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
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
	System.out.println("Sentences Total: " + totalSentences);

	System.out.println("PersonAddresses Total: " + totalPersonAddresses);
	System.out.println("PersonAddresses Total w/negativeSentiment: " + totalPersonAddressesWithNegativeSentiment);
	System.out.println("PersonAddresses Average p/s: " + averagePersonAddressesPerSentence);

	System.out.println("Groups Total: " + totalGroups);
	System.out.println("Groups Total w/negativeSentiment: " + totalGroupsWithNegativeSentiment);
	System.out.println("Groups Average p/s: " + averageGroupsPerSentence);

	System.out.println("Individuals Total: " + totalIndividuals);
	System.out.println("Individuals Total w/negativeSentiment: " + totalIndividualsWithNegativeSentiment);
	System.out.println("Individuals Average p/s: " + averageIndividualsPerSentence);
	System.out.println("Individuals addressed through twitter: " + totalIndividualsAddressThroughTwitter);
	System.out.println("Individuals addressed through twitter w/negativeSentiment: "
		+ totalIndividualsAddressThroughTwitterWithNegativeSentiment);
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
