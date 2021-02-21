package de.uni.due.haring.annotation.analyser.evaluators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.uima.jcas.JCas;

import de.uni.due.haring.annotation.analyser.evaluators.matcher.GroupAffiliationMatcher;
import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public class TargetedGroupEvaluator implements AnnotationEvaluator {
    private JCas jCas;

    public TargetedGroupEvaluator(JCas jCas) {
	this.jCas = jCas;
    }

    private GroupAffiliationType getTargetedGroupAffiliation() {
	ArrayList<GroupAffiliationMatcher> groupAffiliationMatchers = new ArrayList<>();

	Optional<GroupAffiliationMatcher> groupAffiliationMatcher = groupAffiliationMatchers.stream()
		.filter(matcher -> matcher.calculateSimilarityScore()).collect(Collectors.toList()).stream()
		.max(Comparator.comparing(GroupAffiliationMatcher::getSimilarityScore));

	if (groupAffiliationMatcher.isPresent()) {
	    return groupAffiliationMatcher.get().getGroupAffiliationType();
	}

	return GroupAffiliationType.OTHER;
    }

    @Override
    public void processAnnotations() {
	// TODO Auto-generated method stub

    }

    @Override
    public void printEvaluationResults() {
	// TODO Auto-generated method stub

    }

}
