package de.uni.due.haring.annotation.analyser.evaluators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.uni.due.haring.annotation.analyser.evaluators.matcher.EnvironmentalMovementGreensMatcher;
import de.uni.due.haring.annotation.analyser.evaluators.matcher.GeneralPoliticiansMatcher;
import de.uni.due.haring.annotation.analyser.evaluators.matcher.GroupAffiliationMatcher;
import de.uni.due.haring.annotation.analyser.evaluators.matcher.MediaPressMatcher;
import de.uni.due.haring.annotation.analyser.services.AppPrintService;
import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;
import webanno.custom.Zielgruppenadressierung;

public class GroupAffilationLookupEvaluator extends EntityEvaluator implements AnnotationEvaluator {

    private JCas jCas;
    private ArrayList<GroupAffiliationMatcher> groupAffiliationMatchers;

    private int truePositiveGP = 0;
    private int falsePositiveGP = 0;
    private int trueNegativeGP = 0;
    private int falseNegativeGP = 0;

    private int truePositiveMP = 0;
    private int falsePositiveMP = 0;
    private int trueNegativeMP = 0;
    private int falseNegativeMP = 0;

    private int truePositiveGRE = 0;
    private int falsePositiveGRE = 0;
    private int trueNegativeGRE = 0;
    private int falseNegativeGRE = 0;

    public GroupAffilationLookupEvaluator() {
	groupAffiliationMatchers = new ArrayList<>();
	try {
	    initGroupAffilationMatchers();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public GroupAffilationLookupEvaluator(JCas jCas) {
	this.jCas = jCas;
    }

    @Override
    public void processAnnotations() {
	Map<Zielgruppenadressierung, List<String>> personAddressEntities = getPersonAddressEntitiesByPositionWithGroupAffiliation();

	for (Map.Entry<Zielgruppenadressierung, List<String>> entry : personAddressEntities.entrySet()) {

	    Zielgruppenadressierung personAddress = entry.getKey();
	    List<String> groupAffilationGoldLabel = entry.getValue();

	    GroupAffiliationType predictedGroupAffiliation = getTargetedGroupAffiliation(
		    personAddress.getCoveredText());

	    processGeneralPolticsAffiliation(groupAffilationGoldLabel, predictedGroupAffiliation);
	    processMediaPressAffiliation(groupAffilationGoldLabel, predictedGroupAffiliation);
	    processEnvironmentalGreensAffiliation(groupAffilationGoldLabel, predictedGroupAffiliation);

	}
    }

    private GroupAffiliationType getTargetedGroupAffiliation(String entity) {

	Optional<GroupAffiliationMatcher> groupAffiliationMatcher = groupAffiliationMatchers.stream()
		.filter(matcher -> matcher.calculateSimilarityScore(entity)).collect(Collectors.toList()).stream()
		.max(Comparator.comparing(GroupAffiliationMatcher::getSimilarityScore));

	if (groupAffiliationMatcher.isPresent()) {
	    return groupAffiliationMatcher.get().getGroupAffiliationType();
	}

	return GroupAffiliationType.OTHER;
    }

    public Map<Zielgruppenadressierung, List<String>> getPersonAddressEntitiesByPositionWithGroupAffiliation() {
	Map<Zielgruppenadressierung, List<String>> personAddressEntities = new HashMap<>();

	for (Zielgruppenadressierung personAddress : JCasUtil.select(jCas, Zielgruppenadressierung.class)) {
	    boolean skipAddress = false;
//	    if (personAddress.getImplicitAddressing()) {
//		skipAddress = true;
//	    }

	    for (Zielgruppenadressierung za : personAddressEntities.keySet()) {
		if (personAddress.getBegin() == za.getBegin() && personAddress.getEnd() == za.getEnd()) {
		    personAddressEntities.get(za).add(personAddress.getGroupAffiliation());
		    skipAddress = true;
		}
	    }
	    if (!skipAddress) {
		List<String> groupAffiliationGoldLabel = new ArrayList<String>();
		groupAffiliationGoldLabel.add(personAddress.getGroupAffiliation());
		personAddressEntities.put(personAddress, groupAffiliationGoldLabel);
	    }

	}

	return personAddressEntities;
    }

    private void initGroupAffilationMatchers() throws IOException {
	groupAffiliationMatchers.add(new GeneralPoliticiansMatcher());
	groupAffiliationMatchers.add(new MediaPressMatcher());
	groupAffiliationMatchers.add(new EnvironmentalMovementGreensMatcher());
    }

    private void processGeneralPolticsAffiliation(List<String> goldLabel, GroupAffiliationType prediction) {
	if (goldLabel.contains(GroupAffiliationType.GENERAL_POLITICIANS.label)) {
	    if (prediction.label.equals(GroupAffiliationType.GENERAL_POLITICIANS.label)) {
		truePositiveGP++;
	    } else {
		falseNegativeGP++;
	    }
	} else {
	    if (prediction.label.equals(GroupAffiliationType.GENERAL_POLITICIANS.label)) {
		falsePositiveGP++;
	    } else {
		trueNegativeGP++;
	    }
	}
    }

    private void processMediaPressAffiliation(List<String> goldLabel, GroupAffiliationType prediction) {
	if (goldLabel.contains(GroupAffiliationType.MEDIA_PRESS.label)) {
	    if (prediction.label.equals(GroupAffiliationType.MEDIA_PRESS.label)) {
		truePositiveMP++;
	    } else {
		falseNegativeMP++;
	    }
	} else {
	    if (prediction.label.equals(GroupAffiliationType.MEDIA_PRESS.label)) {
		falsePositiveMP++;
	    } else {
		trueNegativeMP++;
	    }
	}
    }

    private void processEnvironmentalGreensAffiliation(List<String> goldLabel, GroupAffiliationType prediction) {
	if (goldLabel.contains(GroupAffiliationType.ENVIRONMENTAL_GREENS.label)) {
	    if (prediction.label.equals(GroupAffiliationType.ENVIRONMENTAL_GREENS.label)) {
		truePositiveGRE++;
	    } else {
		falseNegativeGRE++;
	    }
	} else {
	    if (prediction.label.equals(GroupAffiliationType.ENVIRONMENTAL_GREENS.label)) {
		falsePositiveGRE++;
	    } else {
		trueNegativeGRE++;
	    }
	}
    }

    @Override
    public void printEvaluationResults() {
	AppPrintService.printAutomatedDetection("");
	AppPrintService.printAutomatedDetection("");
	AppPrintService
		.printAutomatedDetection("Results of the experiments on layer 2 (dictionary based approach) ...");

	AppPrintService.printAutomatedDetection("Evaluations for targeted group <GeneralPoltics> ");
	float preGP = getPrecision(truePositiveGP, falsePositiveGP);
	float recGP = getRecall(truePositiveGP, falseNegativeGP);
	AppPrintService.printAutomatedDetection("TruePoisitve: " + truePositiveGP);
	AppPrintService.printAutomatedDetection("FalsePositive: " + falsePositiveGP);
	AppPrintService.printAutomatedDetection("FalseNegative: " + falseNegativeGP);
	AppPrintService.printAutomatedDetection("TrueNegative: " + trueNegativeGP);
	AppPrintService.printAutomatedDetection("Precision: " + preGP);
	AppPrintService.printAutomatedDetection("Recall : " + recGP);
	AppPrintService.printAutomatedDetection("F1: " + getF1Score(preGP, recGP));
	AppPrintService.printAutomatedDetection("");

	AppPrintService.printAutomatedDetection("Evaluations for targeted group <MediaPress> ");
	float preMP = getPrecision(truePositiveMP, falsePositiveMP);
	float recMP = getRecall(truePositiveMP, falseNegativeMP);
	AppPrintService.printAutomatedDetection("TruePoisitve: " + truePositiveMP);
	AppPrintService.printAutomatedDetection("FalsePositive: " + falsePositiveMP);
	AppPrintService.printAutomatedDetection("FalseNegative: " + falseNegativeMP);
	AppPrintService.printAutomatedDetection("TrueNegative: " + trueNegativeMP);
	AppPrintService.printAutomatedDetection("Precision: " + preMP);
	AppPrintService.printAutomatedDetection("Recall : " + recMP);
	AppPrintService.printAutomatedDetection("F1: " + getF1Score(preMP, recMP));
	AppPrintService.printAutomatedDetection("");

	AppPrintService.printAutomatedDetection("Evaluations for targeted group <Env. Movement/Green> ");
	float preGRE = getPrecision(truePositiveGRE, falsePositiveGRE);
	float recGRE = getRecall(truePositiveGRE, falseNegativeGRE);
	AppPrintService.printAutomatedDetection("TruePoisitve: " + truePositiveGRE);
	AppPrintService.printAutomatedDetection("FalsePositive: " + falsePositiveGRE);
	AppPrintService.printAutomatedDetection("FalseNegative: " + falseNegativeGRE);
	AppPrintService.printAutomatedDetection("TrueNegative: " + trueNegativeGRE);
	AppPrintService.printAutomatedDetection("Precision: " + preGRE);
	AppPrintService.printAutomatedDetection("Recall : " + recGRE);
	AppPrintService.printAutomatedDetection("F1: " + getF1Score(preGRE, recGRE));

    }

    public void setjCas(JCas jCas) {
	this.jCas = jCas;
    }

    public float getPrecision(int tp, int fp) {
	return (float) tp / (tp + fp);
    }

    public float getRecall(int tp, int fn) {
	return (float) tp / (tp + fn);
    }

    public float getF1Score(float precision, float recall) {
	return 2 * ((precision * recall) / (precision + recall));
    }

}
