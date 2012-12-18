package com.github.aprestaux.funreco.api;

import java.util.Date;

public class Action {
	private Profile profile;
    private Object object;
    private Date date;

    public Action() {
    }

    public Action(Profile profile, Object object) {
        this.profile = profile;
        this.object = object;
        this.date = new Date();
    }

    public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
