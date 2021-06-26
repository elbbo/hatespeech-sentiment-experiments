package de.uni.due.haring.annotation.analyser.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class AppPrintService {

    public static PrintStream AGREEMENT_PRINT;
    public static PrintStream SURFACE_PRINT;
    public static PrintStream AUTOMATED_DETECTION_PRINT;

    public static void initialize() {
	try {
	    AGREEMENT_PRINT = new PrintStream(
		    new FileOutputStream("src/main/resources/results/annotator_agreement.txt"));
	    SURFACE_PRINT = new PrintStream(new FileOutputStream("src/main/resources/results/surface_structure.txt"));
	    AUTOMATED_DETECTION_PRINT = new PrintStream(
		    new FileOutputStream("src/main/resources/results/automated_detection.txt"));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    public static void printAnnotatorAgreement(String s) {
	print(s, AGREEMENT_PRINT);
    }

    public static void printSurfaceStructure(String s) {
	print(s, SURFACE_PRINT);
    }

    public static void printAutomatedDetection(String s) {
	print(s, AUTOMATED_DETECTION_PRINT);
    }

    private static void print(String s, PrintStream printStream) {
	System.out.println(s);
	printStream.println(s);
    }

}
