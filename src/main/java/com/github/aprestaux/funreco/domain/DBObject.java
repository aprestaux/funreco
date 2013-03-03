package com.github.aprestaux.funreco.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.aprestaux.funreco.api.Object;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;

public class DBObject {
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((objectId == null) ? 0 : objectId.hashCode());
		result = prime
				* result
				+ ((objectProperties == null) ? 0 : objectProperties.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBObject other = (DBObject) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (objectProperties == null) {
			if (other.objectProperties != null)
				return false;
		} else if (!objectProperties.equals(other.objectProperties))
			return false;
		return true;
	}

	@Property
    private Date date;

    @Property
    private String objectId;

    @Embedded
    private Map<String, List<String>> objectProperties;

    public static DBObject fromObject(com.github.aprestaux.funreco.api.Object object) {
        if (object == null) {
            return null;
        }

        DBObject dbObject = new DBObject();
        dbObject.setObjectId(object.getId());
        dbObject.setObjectProperties(object.getAttributes());

        return dbObject;
    }

    public com.github.aprestaux.funreco.api.Object toObject() {
        Object object = new Object();
        object.setId(getObjectId());
        object.getAttributes().putAll(getObjectProperties());

        return object;
    }

    public boolean containsValue(String... values) {
        for (List<String> objectValues : objectProperties.values()) {
            for (String value : values) {
                if (objectValues.contains(value)) {
                    return true;
                }
            }
        }

        return false;
    }

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

    public Map<String, List<String>> getObjectProperties() {
        return objectProperties;
    }

    public void setObjectProperties(Map<String, List<String>> objectProperties) {
        this.objectProperties = objectProperties;
    }
}
