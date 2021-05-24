package de.uni.due.haring.annotation.analyser.evaluators.matcher;

import java.io.IOException;
import java.util.List;

import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public class EnvironmentalMovementGreensMatcher implements GroupAffiliationMatcher {

    private final String FILE_PATH = "./src/main/resources/lists/environmental_green";
    private List<String> listOfEntities;

    private double similarityScore;

    public EnvironmentalMovementGreensMatcher() throws IOException {
	listOfEntities = initEntities(FILE_PATH);
    }

    @Override
    public boolean calculateSimilarityScore(String entity) {
	if (listOfEntities.contains(entity.trim().toLowerCase())) {
	    this.similarityScore = 1f;
	    return true;
	} else {
	    this.similarityScore = 0f;
	    return false;
	}
    }

    @Override
    public double getSimilarityScore() {
	return similarityScore;
    }

    @Override
    public GroupAffiliationType getGroupAffiliationType() {
	return GroupAffiliationType.ENVIRONMENTAL_GREENS;
    }

}
