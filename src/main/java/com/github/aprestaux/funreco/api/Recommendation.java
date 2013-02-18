package com.github.aprestaux.funreco.api;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {
	private String query;

    private List<RecommendedObject> objects = new ArrayList<RecommendedObject>();

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

	public void addObject(RecommendedObject object) {
        objects.add(object);
    }

}
