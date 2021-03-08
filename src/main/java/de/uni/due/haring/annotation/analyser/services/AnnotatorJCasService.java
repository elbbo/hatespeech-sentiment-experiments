package de.uni.due.haring.annotation.analyser.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;

public class AnnotatorJCasService {

    private static List<JCas> anno1JCasList;
    private static List<JCas> anno2JCasList;

    public static void initialize() {
	AnnotatorJCasService.anno1JCasList = new ArrayList<>();
	AnnotatorJCasService.anno2JCasList = new ArrayList<>();
    }

    public static List<JCas> getAnno1JCasList() {
	return anno1JCasList;
    }

    public static void addAnno1JCas(JCas anno1jCas) {
	anno1JCasList.add(anno1jCas);
    }

    public static List<JCas> getAnno2JCasList() {
	return anno2JCasList;
    }

    public static void addAnno2JCas(JCas anno2jCas) {
	anno2JCasList.add(anno2jCas);
    }

}
