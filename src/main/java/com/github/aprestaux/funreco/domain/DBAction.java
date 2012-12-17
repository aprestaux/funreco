package com.github.aprestaux.funreco.domain;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity(value = "actions", noClassnameStored = true)
public class DBAction {
	@Id
    private ObjectId id;

    @Embedded
    private DBProfile profile;

    @Embedded
    private DBObject object;
	
	@Property
	private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public DBObject getObject() {
        return object;
    }

    public void setObject(DBObject object) {
        this.object = object;
    }

    public DBProfile getProfile() {
        return profile;
    }

    public void setProfile(DBProfile profile) {
        this.profile = profile;
    }
}
