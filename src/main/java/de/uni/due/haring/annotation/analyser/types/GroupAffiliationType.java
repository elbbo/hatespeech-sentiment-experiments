package de.uni.due.haring.annotation.analyser.types;

import java.util.HashMap;
import java.util.Map;

public enum GroupAffiliationType {
	OTHER("* sonstige"),
	FOREIGNERS_MIGRATNS("Ausländer / Migranten"),
	DISABLED_SICK("Behinderte / Kranke"),
	ELITES("Eliten"),
	BLACK("Farbige"),
	WOMEN("Frauen"),
	JEWS("Juden"),
	JUDICIAL("Judikative"),
	LGBTQ("LGBTQ+"),
	MEDIA_PRESS("Medien / Presse"),
	MUSLIMS("Muslime"),
	NATIONALITY_ORIGIN("Nationalität / ethnische Herkunft"),
	POLICE("Polizei"),
	GENERAL_POLITICIANS("Regierung / “Die Politik”"),
	RELIGOUS_GROUP("Religionsgruppe"),
	ENVIRONMENTAL_GREENS("Umweltbewegung / Grüne"),
	LEFT_WING("politisches Spektrum links"),
	RIGHT_WING("politisches Spektrum rechts");
	
	private static final Map<String, GroupAffiliationType> BY_LABEL = new HashMap<>();
	
    static {
        for (GroupAffiliationType e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }
	
	public final String label;

	private GroupAffiliationType(String label) {
		this.label = label;
	}
	
    public static GroupAffiliationType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
	
}
