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
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((externalId == null) ? 0 : externalId.hashCode());
		result = prime * result
				+ ((friendsIds == null) ? 0 : friendsIds.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBProfile other = (DBProfile) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (externalId == null) {
			if (other.externalId != null)
				return false;
		} else if (!externalId.equals(other.externalId))
			return false;
		if (friendsIds == null) {
			if (other.friendsIds != null)
				return false;
		} else if (!friendsIds.equals(other.friendsIds))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Id
    private ObjectId id;

    @Property
    private String externalId;

    @Property
    private String email;

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

    public String getEmail() {
        if (attributes.containsKey("email")){
            return attributes.get("email").get(0);
        }else{
            return "unknown email";
        }
    }
}
