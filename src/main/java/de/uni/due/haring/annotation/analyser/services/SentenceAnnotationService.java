package de.uni.due.haring.annotation.analyser.services;

import java.util.ArrayList;
import java.util.List;

import de.uni.due.haring.annotation.analyser.annotations.SentenceAnnotation;

public class SentenceAnnotationService {

	private static List<SentenceAnnotation> sentencesAnnotations = new ArrayList<>();

	public static List<SentenceAnnotation> getSentencesAnnotations() {
		return sentencesAnnotations;
	}

	public static void setSentencesAnnotations(List<SentenceAnnotation> sentencesAnnotations) {
		SentenceAnnotationService.sentencesAnnotations = sentencesAnnotations;
	}
	
}
