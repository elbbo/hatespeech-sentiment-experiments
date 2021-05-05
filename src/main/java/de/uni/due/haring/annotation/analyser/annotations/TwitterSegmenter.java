package de.uni.due.haring.annotation.analyser.annotations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.uni.due.haring.annotation.TwitterUser;

public class TwitterSegmenter extends JCasAnnotator_ImplBase {

    private Pattern TWITTER_USER = Pattern.compile("@(?<=@)[\\w-]+");

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {

	for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
	    Matcher m = TWITTER_USER.matcher(sentence.getCoveredText());

	    while (m.find()) {
		TwitterUser twitterUser = new TwitterUser(aJCas);
		twitterUser.setBegin(m.start());
		twitterUser.setEnd(m.end());
		twitterUser.addToIndexes();
	    }
	}
    }
}
