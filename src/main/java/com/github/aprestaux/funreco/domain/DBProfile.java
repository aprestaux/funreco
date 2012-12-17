package com.github.aprestaux.funreco.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity(value = "profiles", noClassnameStored = true)
public class DBProfile {
    @Id
    private ObjectId id;

    @Embedded
    private String facebookId;

    @Embedded
	private String email;

    @Embedded
	private String name;

    @Property
	private List<String> friendsIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public List<String> getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(List<String> friendsIds) {
        this.friendsIds = friendsIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
