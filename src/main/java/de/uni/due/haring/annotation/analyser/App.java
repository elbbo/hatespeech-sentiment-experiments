package de.uni.due.haring.annotation.analyser;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.dkpro.core.corenlp.CoreNlpNamedEntityRecognizer;
import org.dkpro.core.corenlp.CoreNlpSegmenter;

import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.uni.due.haring.annotation.analyser.analyse.AnalysisAgreementReporter;
import de.uni.due.haring.annotation.analyser.analyse.AnalysisReporter;
import de.uni.due.haring.annotation.analyser.annotations.TwitterSegmenter;
import de.uni.due.haring.annotation.analyser.processors.GoldAnnotationProcessor;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;

public class App {
    public static void main(String[] args) throws UIMAException, IOException {
	AppPrintService.initialize();

	runAgreement();
	runExperiments();
    }

    private static void runExperiments() throws UIMAException, IOException {
	CollectionReader reader = CollectionReaderFactory.createReader(XmiReader.class, XmiReader.PARAM_LANGUAGE, "DE",
		XmiReader.PARAM_SOURCE_LOCATION, "src/main/resources/set-2/", XmiReader.PARAM_PATTERNS, "*.xmi",
		XmiReader.PARAM_TYPE_SYSTEM_FILE, "src/main/resources/set-2/TypeSystem.xml");

	AnalysisEngineDescription goldAnnotationProcessor = AnalysisEngineFactory.createEngineDescription(
		GoldAnnotationProcessor.class, GoldAnnotationProcessor.PARAM_ANNOTATION_DATA_INPUT_FILE_PATH,
		"src/main/resources/subset-gold/subset.2019.training.gold.txt");

	AnalysisEngineDescription seg = AnalysisEngineFactory.createEngineDescription(CoreNlpSegmenter.class,
		CoreNlpSegmenter.PARAM_LANGUAGE, "de");

	AnalysisEngineDescription twitterSeg = AnalysisEngineFactory.createEngineDescription(TwitterSegmenter.class);

	AnalysisEngineDescription pos = AnalysisEngineFactory.createEngineDescription(OpenNlpPosTagger.class,
		OpenNlpPosTagger.PARAM_LANGUAGE, "de");

	AnalysisEngineDescription ner = AnalysisEngineFactory.createEngineDescription(
		CoreNlpNamedEntityRecognizer.class, CoreNlpNamedEntityRecognizer.PARAM_LANGUAGE, "de");

	AnalysisEngineDescription report = AnalysisEngineFactory.createEngineDescription(AnalysisReporter.class);

	SimplePipeline.runPipeline(reader, goldAnnotationProcessor, twitterSeg, pos, ner, report);
    }

    private static void runAgreement() throws UIMAException, IOException {
	CollectionReader agreementReader = CollectionReaderFactory.createReader(XmiReader.class,
		XmiReader.PARAM_LANGUAGE, "DE", XmiReader.PARAM_SOURCE_LOCATION, "src/main/resources/agreement-full/",
		XmiReader.PARAM_PATTERNS, "*.xmi", XmiReader.PARAM_TYPE_SYSTEM_FILE,
		"src/main/resources/agreement-full/TypeSystem.xml");

	AnalysisEngineDescription annotationAgreementReporter = AnalysisEngineFactory.createEngineDescription(
		AnalysisAgreementReporter.class, AnalysisAgreementReporter.PARAM_TYPESYSTEM_DESCRIPTION_PATH,
		"src/main/resources/agreement-full/TypeSystem.xml");

	SimplePipeline.runPipeline(agreementReader, annotationAgreementReporter);
    }
}
