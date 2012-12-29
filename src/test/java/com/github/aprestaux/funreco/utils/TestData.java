package com.github.aprestaux.funreco.utils;

import java.util.Date;

import org.junit.Ignore;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Attributes;
import com.github.aprestaux.funreco.api.Object;

@Ignore("test data factories")
public class TestData {
    public static final String FB_ID = "fbId";

    public static final String FRIEND_FB_ID = "friendFbId";

    public static Attributes testProfileAttributes() {
        return new Attributes().append("mail", "123@test.com").append("name", "Cédric Gignon");
    }

    public static Attributes testFriendProfileAttributes() {
        return new Attributes().append("mail", "friend@test.com").append("name", "friend");
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
}
