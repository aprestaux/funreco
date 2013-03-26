package com.github.aprestaux.funreco.api;

import java.util.List;

public class RecommendedObject {
    private Object object;

    private List<String> by;
    
    private List<String> byEmail;

    public List<String> getByEmail() {
		return byEmail;
	}

	public void setByEmail(List<String> byEmail) {
		this.byEmail = byEmail;
	}

	public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<String> getBy() {
        return by;
    }

    public void setBy(List<String> by) {
        this.by = by;
    }
}
