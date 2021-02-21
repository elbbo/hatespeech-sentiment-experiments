package de.uni.due.haring.annotation.analyser.services;

import org.apache.uima.jcas.JCas;

public class AnnotatorJCasService {

    private static JCas anno1JCas;
    private static JCas anno2JCas;

    public static JCas getAnno1JCas() {
	return anno1JCas;
    }

    public static void setAnno1JCas(JCas anno1jCas) {
	anno1JCas = anno1jCas;
    }

    public static JCas getAnno2JCas() {
	return anno2JCas;
    }

    public static void setAnno2JCas(JCas anno2jCas) {
	anno2JCas = anno2jCas;
    }

}
