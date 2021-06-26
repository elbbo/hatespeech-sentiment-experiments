package de.uni.due.haring.annotation.analyser.analyse;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.uni.due.haring.annotation.analyser.evaluators.CoreNlpNamedEntityEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.GermEvalAnnotationEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.GroupAffilationLookupEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.GroupAffiliationEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.GroupAffiliationWordCountEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.ImplicitAnnotationEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.NegativeSentimentPredictionEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.NegativeSentimentSentencePredictionEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.OpenNLPPosTaggerEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.PersonAddressEntityEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.PersonAddressEvaluator;
import de.uni.due.haring.annotation.analyser.evaluators.TwitterEntityEvaluator;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;

public class AnalysisReporter extends JCasAnnotator_ImplBase {

    CoreNlpNamedEntityEvaluator coreNlpNamedEntityEvaluator;
    OpenNLPPosTaggerEvaluator openNlpPostTaggerEvaluator;
    TwitterEntityEvaluator twitterEntityEvaluator;
    PersonAddressEntityEvaluator personAddressEntityEvaluator;
    GroupAffilationLookupEvaluator generalPolticsGroupAffilationEvaluator;
    NegativeSentimentSentencePredictionEvaluator negativeSentimentSentencePredictionEvaluator;
    NegativeSentimentPredictionEvaluator negativeSentimentPredictionEvaluator;

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
	super.initialize(context);
	this.coreNlpNamedEntityEvaluator = new CoreNlpNamedEntityEvaluator();
	this.openNlpPostTaggerEvaluator = new OpenNLPPosTaggerEvaluator();
	this.twitterEntityEvaluator = new TwitterEntityEvaluator();
	this.personAddressEntityEvaluator = new PersonAddressEntityEvaluator();
	this.generalPolticsGroupAffilationEvaluator = new GroupAffilationLookupEvaluator();
	this.negativeSentimentSentencePredictionEvaluator = new NegativeSentimentSentencePredictionEvaluator();
	this.negativeSentimentPredictionEvaluator = new NegativeSentimentPredictionEvaluator();
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
	coreNlpNamedEntityEvaluator.setjCas(jCas);
	coreNlpNamedEntityEvaluator.processAnnotations();
	openNlpPostTaggerEvaluator.setjCas(jCas);
	openNlpPostTaggerEvaluator.processAnnotations();
	twitterEntityEvaluator.setjCas(jCas);
	twitterEntityEvaluator.processAnnotations();
	personAddressEntityEvaluator.setjCas(jCas);
	personAddressEntityEvaluator.processAnnotations();

	generalPolticsGroupAffilationEvaluator.setjCas(jCas);
	generalPolticsGroupAffilationEvaluator.processAnnotations();

	negativeSentimentSentencePredictionEvaluator.setjCas(jCas);
	negativeSentimentSentencePredictionEvaluator.processAnnotations();

	negativeSentimentPredictionEvaluator.setjCas(jCas);
	negativeSentimentPredictionEvaluator.processAnnotations();
    }

    @Override
    public void collectionProcessComplete() throws AnalysisEngineProcessException {
	super.collectionProcessComplete();

	PersonAddressEvaluator personAddressEvaluator = new PersonAddressEvaluator();
	GroupAffiliationEvaluator groupAffiliationsEvaluator = new GroupAffiliationEvaluator();
	ImplicitAnnotationEvaluator implcitAnnotationEvaluator = new ImplicitAnnotationEvaluator();
	GermEvalAnnotationEvaluator germEvalAnnotationEvaluator = new GermEvalAnnotationEvaluator();
	GroupAffiliationWordCountEvaluator groupAffiliationWordCountEvaluator = new GroupAffiliationWordCountEvaluator();

	personAddressEvaluator.processAnnotations();
	groupAffiliationsEvaluator.processAnnotations();
	implcitAnnotationEvaluator.processAnnotations();
	germEvalAnnotationEvaluator.processAnnotations();
	groupAffiliationWordCountEvaluator.processAnnotations();

	personAddressEvaluator.printEvaluationResults();
	groupAffiliationsEvaluator.printEvaluationResults();
	implcitAnnotationEvaluator.printEvaluationResults();
	germEvalAnnotationEvaluator.printEvaluationResults();
	groupAffiliationWordCountEvaluator.printEvaluationResults();

	AppPrintService.printAutomatedDetection("Evaluations of the Automated Detection ...");
	coreNlpNamedEntityEvaluator.printEvaluationResults();
	openNlpPostTaggerEvaluator.printEvaluationResults();
	twitterEntityEvaluator.printEvaluationResults();
	personAddressEntityEvaluator.printEvaluationResults();

	generalPolticsGroupAffilationEvaluator.printEvaluationResults();
	negativeSentimentSentencePredictionEvaluator.printEvaluationResults();
	negativeSentimentPredictionEvaluator.printEvaluationResults();
    }

}
