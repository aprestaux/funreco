package com.github.aprestaux.funreco.api;

import java.util.Date;

public class Action {
	private Object object;

    private Date date;

    public Action() {
    }

    public Action(Object object) {
        this.object = object;
        this.date = new Date();
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
