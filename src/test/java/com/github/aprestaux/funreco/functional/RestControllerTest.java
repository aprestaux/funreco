package com.github.aprestaux.funreco.functional;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.github.aprestaux.funreco.IntegrationSpringConfig;
import com.github.aprestaux.funreco.api.Friend;
import com.github.aprestaux.funreco.api.Friends;
import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.domain.DBProfile;
import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;

import static com.github.aprestaux.funreco.TestData.*;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Fire tomcat with "mvn -Dspring.profiles.active=integration tomcat:run" to activate integration profile
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = "integration")
public class RestControllerTest {
    @Configuration
    @Import(IntegrationSpringConfig.class)
    static class ContextConfiguration {

    }

    @Inject
    private Datastore datastore;

    @Inject
    private Mongo mongo;

    @Before
    public void clean() {
        mongo.dropDatabase(IntegrationSpringConfig.DB_NAME);
    }

    @Test
    public void postProfile() {
        doSaveProfile(testProfile());

        assertThat(datastore.find(DBProfile.class).countAll()).isEqualTo(1);
    }

    @Test
    public void getProfile() {
        doSaveProfile(testProfile());

        Profile profile = expect().statusCode(200).when().get("/api/profiles/" + TEST_FB_ID).as(Profile.class);

        assertThat(profile.getName()).isEqualTo(testProfile().getName());
    }

    @Test
    public void putFriends() {
        doSaveProfile(testProfile());
        doPutFriends(TEST_FB_ID, toFriends(testFriendProfile()));

        Friends friends = doGetFriends(TEST_FB_ID);

        assertThat(friends.size()).isEqualTo(1);
        assertThat(friends.get(0).getFacebookId()).isEqualTo(testFriendProfile().getFacebookId());
    }

    private void doSaveProfile(Profile profile) {
        given().contentType("application/json").body(profile)
                .expect().statusCode(200)
                .when().post("/api/profiles");
    }

    private void doPutFriends(String facebookId, List<Friend> friends) {
        given().contentType("application/json").body(friends)
                .expect().statusCode(200)
                .when().put("/api/profiles/" + facebookId + "/friends");
    }

    private Friends doGetFriends(String facebookId) {
        return expect().statusCode(200)
                .when().get("/api/profiles/" + facebookId + "/friends").as(Friends.class);
    }
}
