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

    public AnnotationUnit() {
	anno1Layer1Features = new ArrayList<>();
	anno2Layer1Features = new ArrayList<>();
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

}