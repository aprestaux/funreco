package com.github.aprestaux.funreco.api;

public class Object {
	private String id;

    private Attributes attributes = new Attributes();

    public Object() {
    }

    public Object(String id) {
        this.id = id;
    }

    public void putAttributes(String key, String... values) {
        attributes.put(key, values);
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}


}
