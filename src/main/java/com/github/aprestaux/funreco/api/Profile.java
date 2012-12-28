package com.github.aprestaux.funreco.api;

public class Profile {
	private String id;

    private Attributes attributes = new Attributes();

    public Profile() {
    }

    public Profile(String id) {
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

    @Override
    public String toString() {
        return "Profile{" +
                "id='" + id + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
