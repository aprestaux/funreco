package com.github.aprestaux.funreco.domain;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;

public class DBObject {
    @Property
    private Date date;

    @Property
    private String objectId;

    @Embedded
    private Map<String, Set<String>> objectProperties;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Map<String, Set<String>> getObjectProperties() {
        return objectProperties;
    }

    public void setObjectProperties(Map<String, Set<String>> objectProperties) {
        this.objectProperties = objectProperties;
    }
}
