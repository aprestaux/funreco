package com.github.aprestaux.funreco.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.github.aprestaux.funreco.api.Attributes;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity(value = "profiles", noClassnameStored = true)
public class DBProfile {
    @Id
    private ObjectId id;

    @Property
    private String externalId;

    @Embedded
	private Attributes attributes = new Attributes();

    @Property
	private List<String> friendsIds;

    public DBProfile() {
    }

    public DBProfile(String externalId) {
        this.externalId = externalId;
    }

    public void putAttributes(String key, String... values) {
        attributes.put(key, values);
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public List<String> getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(List<String> friendsIds) {
        this.friendsIds = friendsIds;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}
