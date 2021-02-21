package de.uni.due.haring.annotation.analyser.evaluators.matcher;

import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public class MediaPressMatcher implements GroupAffiliationMatcher {

    private double similarityScore;

    @Override
    public boolean calculateSimilarityScore() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public double getSimilarityScore() {
	return similarityScore;
    }

    @Override
    public GroupAffiliationType getGroupAffiliationType() {
	return GroupAffiliationType.MEDIA_PRESS;
    }

}
