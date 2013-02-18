package com.github.aprestaux.funreco.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Recommendations {
	private String profileId;

	private Map<String, Recommendation> recommendations = new HashMap<String, Recommendation>();

    public Recommendation first() {
        if (recommendations == null || recommendations.size() == 0) {
            return null;
        } else {
            return getEntries().iterator().next();
        }
    }

	public Collection<Recommendation> getEntries() {
		return recommendations.values();
	}

	public Recommendation forQuery(String query) {
		return recommendations.get(query);
	}

	public void addRecommendation(Recommendation recommendation) {
		recommendations.put(recommendation.getQuery(), recommendation);
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
}
