package com.github.aprestaux.funreco.utils;

import java.util.Date;

import org.junit.Ignore;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Friend;
import com.github.aprestaux.funreco.api.Friends;
import com.github.aprestaux.funreco.api.Object;
import com.github.aprestaux.funreco.api.Profile;

@Ignore("test data factories")
public class TestData {
    public static final String FB_ID = "fbId";

    public static final String FRIEND_FB_ID = "friendFbId";
    
    public static Profile testProfile() {
        Profile profile = new Profile(FB_ID);
        profile.setEmail("123@test.com");
        profile.setName("Cédric Gignon");
        return profile;
    }

    public static Profile testFriendProfile() {
        Profile profile = new Profile(FRIEND_FB_ID);
        profile.setEmail("friend@test.com");
        profile.setName("friend");
        return profile;
    }

    public static Action testAction() {
        Object object = new Object("objectId");
        object.putAttributes("type", "news");

        Action action = new Action();
        action.setDate(new Date());
        action.setObject(object);
        return action;
    }

    public static Object testObject() {
        Object object = new Object("publicObjectId");

        object.putAttributes("show", "musique", "dance");

        return object;
    }

    public static Friend toFriend(Profile profile) {
        Friend friend = new Friend();
        friend.setId(profile.getId());
        return friend;
    }

    public static Friends toFriends(Profile... profiles) {
        Friends friends = new Friends();

        for (Profile profile : profiles) {
            friends.add(toFriend(profile));
        }

        return friends;
    }
}
