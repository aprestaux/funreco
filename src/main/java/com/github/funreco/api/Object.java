package com.github.funreco.api;

import java.util.ArrayList;
import java.util.Map;

public class Object {
	private String id;
    private Map<String, ArrayList<String>> objectProperties;
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, ArrayList<String>> getObjectProperties() {
		return objectProperties;
	}

	public void setObjectProperties(Map<String, ArrayList<String>> objectProperties) {
		this.objectProperties = objectProperties;
	}

	@Override
	public String toString() {
        return "Object{" +
                "id='" + id + '\'' +
                ", properties=" + objectProperties +
                '}';
    }
	
	public boolean equals(Object object) {
		return (this.id == object.id && this.objectProperties.equals(object.objectProperties));
	}

}
