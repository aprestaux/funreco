package com.github.aprestaux.funreco.api;

import java.util.List;
import java.util.Map;

public class Object {
	private String id;

    private Map<String, List<String>> objectProperties;
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, List<String>> getObjectProperties() {
		return objectProperties;
	}

	public void setObjectProperties(Map<String, List<String>> objectProperties) {
		this.objectProperties = objectProperties;
	}

	@Override
	public String toString() {
        return "Object{" +
                "id='" + id + '\'' +
                ", properties=" + objectProperties +
                '}';
    }

}
