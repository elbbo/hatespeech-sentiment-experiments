package de.uni.due.haring.annotation.analyser.annotations;

import java.util.List;

public class SentenceAnnotation {

    private String documentText;
    private List<PersonAddress> personAddresses;
    private String offenseGoldAnnotation;
    private boolean isOffensive;
    private boolean hasNegativeSentiment;

    public String getDocumentText() {
	return documentText;
    }

    public void setDocumentText(String documentText) {
	this.documentText = documentText;
    }

    public List<PersonAddress> getPersonAddresses() {
	return personAddresses;
    }

    public void setPersonAddresses(List<PersonAddress> personAddresses) {
	this.personAddresses = personAddresses;
    }

    public String getOffenseGoldAnnotation() {
	return offenseGoldAnnotation;
    }

    public void setOffenseGoldAnnotation(String offenseGoldAnnotation) {
	this.offenseGoldAnnotation = offenseGoldAnnotation;
    }

    public boolean isOffensive() {
	return isOffensive;
    }

    public void setOffensive(boolean isOffensive) {
	this.isOffensive = isOffensive;
    }

    public boolean hasNegativeSentiment() {
	return hasNegativeSentiment;
    }

    public void setHasNegativeSentiment(boolean hasNegativeSentiment) {
	this.hasNegativeSentiment = hasNegativeSentiment;
    }

}
