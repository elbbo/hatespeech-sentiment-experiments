package de.uni.due.haring.annotation.analyser.annotations;

import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public class PersonAddress {

    private String coveredText;
    private String addressType;
    private boolean isGroupAddress;
    private GroupAffiliationType groupAffiliation;
    private boolean hasNegativeSentiment;
    private boolean isImplicit;

    public String getCoveredText() {
	return coveredText;
    }

    public void setCoveredText(String coveredText) {
	this.coveredText = coveredText;
    }

    public String getAddressType() {
	return addressType;
    }

    public void setAddressType(String addressType) {
	this.addressType = addressType;
    }

    public boolean isGroupAddress() {
	return isGroupAddress;
    }

    public void setGroupAddress(boolean isGroupAddress) {
	this.isGroupAddress = isGroupAddress;
    }

    public GroupAffiliationType getGroupAffiliation() {
	return groupAffiliation;
    }

    public void setGroupAffiliation(GroupAffiliationType groupAffiliation) {
	this.groupAffiliation = groupAffiliation;
    }

    public boolean hasNegativeSentiment() {
	return hasNegativeSentiment;
    }

    public void setHasNegativeSentiment(boolean hasNegativeSentiment) {
	this.hasNegativeSentiment = hasNegativeSentiment;
    }

    public boolean isImplicit() {
	return isImplicit;
    }

    public void setImplicit(boolean isImplicit) {
	this.isImplicit = isImplicit;
    }

    @Override
    public String toString() {
	return "PersonAddress [coveredText=" + coveredText + ", addressType=" + addressType + ", isGroupAddress="
		+ isGroupAddress + ", groupAffiliation=" + groupAffiliation + ", hasNegativeSentiment="
		+ hasNegativeSentiment + ", isImplicit=" + isImplicit + "]";
    }

}
