package de.uni.due.haring.annotation.analyser.processors;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

public class AnnotationAgreementProcessor extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
	jCas.getDocumentAnnotationFs();
	
	int count = 0;
	for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
	    count++;
	}
	System.out.println(count);
    }

}
