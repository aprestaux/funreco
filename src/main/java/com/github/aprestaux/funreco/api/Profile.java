package com.github.aprestaux.funreco.api;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Angela
 * Date: 20/03/13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class Profile {
    private String externalId;

    private String email;

    private List<String> friendsIds;

    public Profile() {
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(List<String> friendsIds) {
        this.friendsIds = friendsIds;
    }
}
