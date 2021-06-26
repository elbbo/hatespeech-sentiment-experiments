package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.uni.due.haring.annotation.analyser.annotations.PersonAddress;
import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;

public class ImplicitAnnotationEvaluator implements AnnotationEvaluator {

    private List<SentenceAnnotation> sentenceAnnotations;

    private int totalExplicit;
    private int totalImplicit;

    private int totalExplicitWithNegativeSentiment;
    private int totalImplicitWithNegativeSentiment;
    private int totalImplicitWithNegativeSentimentNoExplicit;

    private int totalImplicitGroupAnnotations;
    private List<PersonAddress> implicitIndividualAnnotations;

    public ImplicitAnnotationEvaluator() {
	this.sentenceAnnotations = SentenceAnnotationService.getSentencesAnnotations();
    }

    @Override
    public void processAnnotations() {
	totalExplicit = getExplicitAnnotations();
	totalImplicit = getImplicitAnnotations();

	totalExplicitWithNegativeSentiment = getExplicitAnnotationsWithNegativeSentiment();
	totalImplicitWithNegativeSentiment = getImplicitAnnotationsWithNegativeSentiment();

	totalImplicitGroupAnnotations = getImplicitGroupAnnotations();

	implicitIndividualAnnotations = getImplicitIAnnotations();
	calculateImplicitAnnotationsWithNegativeSentimentNoExplicit();
    }

    @Override
    public void printEvaluationResults() {
	AppPrintService.printSurfaceStructure("");
	AppPrintService.printSurfaceStructure("");

	AppPrintService.printSurfaceStructure("Number of explicit addressings Total: " + totalExplicit);
	AppPrintService.printSurfaceStructure(
		"Number of explicit addressings Total w/negativeSentiment: " + totalExplicitWithNegativeSentiment);

	AppPrintService.printSurfaceStructure("Number of implicit addressings Total: " + totalImplicit);
	AppPrintService.printSurfaceStructure(
		"Number of implicit addressings Total w/negativeSentiment: " + totalImplicitWithNegativeSentiment);
	AppPrintService.printSurfaceStructure(
		"Number of implicit addressings Total w/negativeSentiment / no explicit in Sentence: "
			+ totalImplicitWithNegativeSentimentNoExplicit);

	AppPrintService
		.printSurfaceStructure("Number of implicit group addressings Total: " + totalImplicitGroupAnnotations);
	// implicitIndividualAnnotations.forEach(pa ->
	// System.out.println(pa.getCoveredText()));
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

    private int getImplicitGroupAnnotations() {
	return sentenceAnnotations.stream()
		.mapToInt(sentenceAnnotation -> sentenceAnnotation.getPersonAddresses().stream()
			.filter(pa -> pa.isImplicit() && pa.isGroupAddress()).collect(Collectors.toList()).size())
		.sum();
    }

    private List<PersonAddress> getImplicitIAnnotations() {
	List<PersonAddress> annotations = new ArrayList<>();
	sentenceAnnotations.stream().forEach(sentenceAnnotation -> {
	    List<PersonAddress> personAddresses = sentenceAnnotation.getPersonAddresses().stream()
		    .filter(pa -> pa.isImplicit() && pa.isGroupAddress()).collect(Collectors.toList());
	    if (personAddresses.size() > 0)
		annotations.addAll(personAddresses);
	});
	return annotations;
    }

}
