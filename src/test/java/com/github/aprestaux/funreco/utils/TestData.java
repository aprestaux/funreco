package com.github.aprestaux.funreco.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.aprestaux.funreco.api.Friend;
import com.github.aprestaux.funreco.api.Friends;
import com.github.aprestaux.funreco.api.Object;
import com.github.aprestaux.funreco.api.Profile;

public class TestData {
    public static final String FB_ID = "fbId";

    public static final String FRIEND_FB_ID = "friendFbId";
    
    public static Profile testProfile() {
        Profile profile = new Profile();
        profile.setFacebookId(FB_ID);
        profile.setEmail("123@test.com");
        profile.setName("123");
        return profile;
    }

    public static Profile testFriendProfile() {
        Profile profile = new Profile();
        profile.setFacebookId(FRIEND_FB_ID);
        profile.setEmail("friend@test.com");
        profile.setName("friend");
        return profile;
    }

    public static com.github.aprestaux.funreco.api.Object testObject() {
        Object object = new Object();

        object.setId("publicObjectId");

        Map<String, List<String>> objectProperties = new HashMap<String, List<String>>();
        objectProperties.put("show", Arrays.asList("musique", "dance"));

        object.setObjectProperties(objectProperties);

        return object;
    }

    public static Friend toFriend(Profile profile) {
        Friend friend = new Friend();
        friend.setFacebookId(profile.getFacebookId());
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
