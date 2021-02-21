package de.uni.due.haring.annotation.analyser.evaluators.matcher;

import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public interface GroupAffiliationMatcher {

    boolean calculateSimilarityScore();
    
    double getSimilarityScore();

    GroupAffiliationType getGroupAffiliationType();
}
