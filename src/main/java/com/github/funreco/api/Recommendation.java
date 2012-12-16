package com.github.funreco.api;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {
	private String query;
	
    public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<RecommendedObject> getObjects() {
		return objects;
	}

	public void setObjects(List<RecommendedObject> objects) {
		this.objects = objects;
	}

	private List<RecommendedObject> objects = new ArrayList<RecommendedObject>();

    public void addObject(RecommendedObject object) {
        objects.add(object);
    }
	
	public boolean equals(Recommendation recommendation) {
		return (this.objects.equals(recommendation.objects) && this.query == recommendation.query);
	}

}
