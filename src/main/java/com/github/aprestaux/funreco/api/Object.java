package com.github.aprestaux.funreco.api;

public class Object {
	private String id;

    private ObjectProperties properties = new ObjectProperties();

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ObjectProperties getProperties() {
		return properties;
	}

	public void setProperties(ObjectProperties properties) {
		this.properties = properties;
	}


}
