package de.uni.due.haring.annotation.analyser.analyse;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.uni.due.haring.annotation.analyser.evaluators.CoreNlpNamedEntityEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.GermEvalAnnotationEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.GroupAffiliationEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.ImplicitAnnotationEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.PersonAddressEvaluator;
import de.uni.due.haring.annotation.analyser.services.SentenceAnnotationService;

public class AnalysisReporter extends JCasAnnotator_ImplBase {

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
	super.initialize(context);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
	CoreNlpNamedEntityEvaluator coreNlpNamedEntityEvaluator = new CoreNlpNamedEntityEvaluator(jCas);
	coreNlpNamedEntityEvaluator.processAnnotations();
	
	
    }


    @Override
    public void collectionProcessComplete() throws AnalysisEngineProcessException {
	super.collectionProcessComplete();
	
	PersonAddressEvaluator personAddressEvaluator = new PersonAddressEvaluator();
	GroupAffiliationEvaluator groupAffiliationsEvaluator = new GroupAffiliationEvaluator();
	ImplicitAnnotationEvaluator implcitAnnotationEvaluator = new ImplicitAnnotationEvaluator();
	GermEvalAnnotationEvaluator germEvalAnnotationEvaluator = new GermEvalAnnotationEvaluator();
	
	personAddressEvaluator.processAnnotations();
	groupAffiliationsEvaluator.processAnnotations();
	implcitAnnotationEvaluator.processAnnotations();
	germEvalAnnotationEvaluator.processAnnotations();
	
	personAddressEvaluator.printEvaluationResults();
	groupAffiliationsEvaluator.printEvaluationResults();
	implcitAnnotationEvaluator.printEvaluationResults();
	germEvalAnnotationEvaluator.printEvaluationResults();

    }

}
