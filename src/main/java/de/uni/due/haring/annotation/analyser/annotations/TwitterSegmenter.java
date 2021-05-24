package de.uni.due.haring.annotation.analyser.annotations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import de.uni.due.haring.annotation.TwitterUser;

public class TwitterSegmenter extends JCasAnnotator_ImplBase {

    private Pattern TWITTER_USER = Pattern.compile("@(?<=@)[\\w-]+");

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
	
	Matcher m = TWITTER_USER.matcher(aJCas.getDocumentText());
	while (m.find()) {
	    TwitterUser twitterUser = new TwitterUser(aJCas);
	    twitterUser.setBegin(m.start());
	    twitterUser.setEnd(m.end());
	    twitterUser.setCoveredText(m.group(0));
	    twitterUser.addToIndexes();

	}
	
    }
}
