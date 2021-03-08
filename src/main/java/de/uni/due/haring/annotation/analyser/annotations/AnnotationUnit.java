package de.uni.due.haring.annotation.analyser.annotations;

import java.util.ArrayList;
import java.util.List;

public class AnnotationUnit {

    private int begin;
    private int end;
    private boolean anno1Present;
    private boolean anno2Present;
    private List<String> anno1Layer1Features;
    private List<String> anno2Layer1Features;
    private List<String> anno1Layer2Features;
    private List<String> anno2Layer2Features;
    private List<Boolean> anno1Layer3Features;
    private List<Boolean> anno2Layer3Features;
    private List<Boolean> anno1Layer4Features;
    private List<Boolean> anno2Layer4Features;
    private String documentText;
    private String annotationText;

    public AnnotationUnit() {
	anno1Layer1Features = new ArrayList<>();
	anno2Layer1Features = new ArrayList<>();
	anno1Layer2Features = new ArrayList<>();
	anno2Layer2Features = new ArrayList<>();
	anno1Layer3Features = new ArrayList<>();
	anno2Layer3Features = new ArrayList<>();
	anno1Layer4Features = new ArrayList<>();
	anno2Layer4Features = new ArrayList<>();
    }

    public int getBegin() {
	return begin;
    }

    public void setBegin(int begin) {
	this.begin = begin;
    }

    public int getEnd() {
	return end;
    }

    public void setEnd(int end) {
	this.end = end;
    }

    public boolean isAnno1Present() {
	return anno1Present;
    }

    public void setAnno1Present(boolean anno1Present) {
	this.anno1Present = anno1Present;
    }

    public boolean isAnno2Present() {
	return anno2Present;
    }

    public void setAnno2Present(boolean anno2Prestent) {
	this.anno2Present = anno2Prestent;
    }

    public boolean isMatch() {
	return anno1Present && anno2Present;
    }

    public List<String> getAnno1Layer1Features() {
	return anno1Layer1Features;
    }

    public void setAnno1Layer1Features(List<String> anno1Layer1Features) {
	this.anno1Layer1Features = anno1Layer1Features;
    }

    public List<String> getAnno2Layer1Features() {
	return anno2Layer1Features;
    }

    public void setAnno2Layer1Features(List<String> anno2Layer1Features) {
	this.anno2Layer1Features = anno2Layer1Features;
    }

    public List<String> getAnno1Layer2Features() {
	return anno1Layer2Features;
    }

    public void setAnno1Layer2Features(List<String> anno1Layer2Features) {
	this.anno1Layer2Features = anno1Layer2Features;
    }

    public List<String> getAnno2Layer2Features() {
	return anno2Layer2Features;
    }

    public void setAnno2Layer2Features(List<String> anno2Layer2Features) {
	this.anno2Layer2Features = anno2Layer2Features;
    }

    public List<Boolean> getAnno1Layer3Features() {
	return anno1Layer3Features;
    }

    public void setAnno1Layer3Features(List<Boolean> anno1Layer3Features) {
	this.anno1Layer3Features = anno1Layer3Features;
    }

    public List<Boolean> getAnno2Layer3Features() {
	return anno2Layer3Features;
    }

    public void setAnno2Layer3Features(List<Boolean> anno2Layer3Features) {
	this.anno2Layer3Features = anno2Layer3Features;
    }

    public List<Boolean> getAnno1Layer4Features() {
	return anno1Layer4Features;
    }

    public void setAnno1Layer4Features(List<Boolean> anno1Layer4Features) {
	this.anno1Layer4Features = anno1Layer4Features;
    }

    public List<Boolean> getAnno2Layer4Features() {
	return anno2Layer4Features;
    }

    public void setAnno2Layer4Features(List<Boolean> anno2Layer4Features) {
	this.anno2Layer4Features = anno2Layer4Features;
    }

    public String getDocumentText() {
	return documentText;
    }

    public void setDocumentText(String documentText) {
	this.documentText = documentText;
    }

    public String getAnnotationText() {
	return annotationText;
    }

    public void setAnnotationText(String annotationText) {
	this.annotationText = annotationText;
    }

}