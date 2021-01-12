package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.List;
import java.util.stream.Collectors;

import de.uni.due.haring.annotation.analyser.annotations.PersonAddress;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;

public class ImplicitAnnotationEvaluator implements AnnotationEvaluator {

    private List<SentenceAnnotation> sentenceAnnotations;

    private int totalExplicit;
    private int totalImplicit;

    private int totalExplicitWithNegativeSentiment;
    private int totalImplicitWithNegativeSentiment;
    private int totalImplicitWithNegativeSentimentNoExplicit;

    public ImplicitAnnotationEvaluator() {
	this.sentenceAnnotations = SentenceAnnotationService.getSentencesAnnotations();
    }

    @Override
    public void processAnnotations() {
	totalExplicit = getExplicitAnnotations();
	totalImplicit = getImplicitAnnotations();

	totalExplicitWithNegativeSentiment = getExplicitAnnotationsWithNegativeSentiment();
	totalImplicitWithNegativeSentiment = getImplicitAnnotationsWithNegativeSentiment();

	calculateImplicitAnnotationsWithNegativeSentimentNoExplicit();
    }

    @Override
    public void printEvaluationResults() {
	System.out.println("Explicit Total: " + totalExplicit);
	System.out.println("Explicit Total w/negativeSentiment: " + totalExplicitWithNegativeSentiment);

	System.out.println("Implicit Total: " + totalImplicit);
	System.out.println("Implicit Total w/negativeSentiment: " + totalImplicitWithNegativeSentiment);
	System.out.println("Implicit Total w/negativeSentiment / no explicit in Sentence: " + totalImplicitWithNegativeSentimentNoExplicit);
    }

    private void calculateImplicitAnnotationsWithNegativeSentimentNoExplicit() {
	sentenceAnnotations.stream().forEach(sentenceAnnotation -> {
	    List<PersonAddress> explicitWithNegativeSentiment = sentenceAnnotation.getPersonAddresses().stream()
		    .filter(pa -> !pa.isImplicit() && pa.hasNegativeSentiment()).collect(Collectors.toList());
	    if (explicitWithNegativeSentiment.isEmpty()) {
		List<PersonAddress> implicitWithNegativeSentiment = sentenceAnnotation.getPersonAddresses().stream()
			.filter(pa -> pa.isImplicit() && pa.hasNegativeSentiment()).collect(Collectors.toList());
		totalImplicitWithNegativeSentimentNoExplicit += implicitWithNegativeSentiment.size();
	    }
	});
    }

    private int getImplicitAnnotationsWithNegativeSentiment() {
	return sentenceAnnotations.stream()
		.mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses().stream()
			.filter(pa -> pa.isImplicit() && pa.hasNegativeSentiment()).collect(Collectors.toList()).size())
		.sum();
    }

    private int getExplicitAnnotationsWithNegativeSentiment() {
	return sentenceAnnotations.stream()
		.mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses().stream()
			.filter(pa -> !pa.isImplicit() && pa.hasNegativeSentiment()).collect(Collectors.toList())
			.size())
		.sum();
    }

    private int getImplicitAnnotations() {
	return sentenceAnnotations.stream().mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses()
		.stream().filter(pa -> pa.isImplicit()).collect(Collectors.toList()).size()).sum();
    }

    private int getExplicitAnnotations() {
	return sentenceAnnotations.stream().mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses()
		.stream().filter(pa -> !pa.isImplicit()).collect(Collectors.toList()).size()).sum();
    }

}
