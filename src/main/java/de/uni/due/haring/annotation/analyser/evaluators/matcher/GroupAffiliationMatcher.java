package de.uni.due.haring.annotation.analyser.evaluators.matcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni.due.haring.annotation.analyser.types.GroupAffiliationType;

public interface GroupAffiliationMatcher {

    boolean calculateSimilarityScore(String entity);

    double getSimilarityScore();

    GroupAffiliationType getGroupAffiliationType();

    default List<String> initEntities(String filePath) throws IOException {
	List<String> listOfEntities = new ArrayList<>();

	String target_dir = filePath;
	File dir = new File(target_dir);
	File[] files = dir.listFiles();

	for (File f : files) {
	    if (f.isFile()) {
		BufferedReader inputStream = null;

		try {
		    inputStream = new BufferedReader(new FileReader(f));
		    String line;

		    while ((line = inputStream.readLine()) != null) {
			listOfEntities.add(line.trim().toLowerCase());
		    }
		} finally {
		    if (inputStream != null) {
			inputStream.close();
		    }
		}
	    }
	}
	return listOfEntities;
    }
}
