package com.github.aprestaux.funreco.domain;

import java.util.Date;
import java.util.Map;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity(value = "recommendations", noClassnameStored = true)
public class DBRecommendations {
    @Id
    private ObjectId id;

    @Embedded
    private DBProfile profile;

    @Embedded
	private Map<DBObject,Integer> recommendations;

    @Property
	private Date date;

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

    public DBProfile getProfile() {
        return profile;
    }

    public void setProfile(DBProfile profile) {
        this.profile = profile;
    }

    public Map<DBObject, Integer> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Map<DBObject, Integer> recommendations) {
        this.recommendations = recommendations;
    }
}
