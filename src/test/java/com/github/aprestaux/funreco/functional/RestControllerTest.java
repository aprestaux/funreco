package com.github.aprestaux.funreco.functional;

import java.util.Arrays;
import java.util.List;

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
import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Attributes;

import static com.github.aprestaux.funreco.utils.TestData.*;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Fire tomcat with "mvn tomcat:run -Dspring.profiles.active=functional" to activate integration profile
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = "functional")
public class RestControllerTest {
    @Configuration
    @Import(IntegrationSpringConfig.class)
    static class ContextConfiguration {

    }

    @Before
    public void clean() {

    }

    @Test
    public void putProfile() {
        doSaveProfile(FB_ID, testProfileAttributes());
    }

    @Test
    public void getProfile() {
        doSaveProfile(FB_ID, testProfileAttributes());

        Attributes attributes = expect().statusCode(200).when().get("/api/profiles/" + FB_ID).as(Attributes.class);

        assertThat(attributes.get("name")).isEqualTo(testProfileAttributes().get("name"));
    }

    @Test
    public void putFriends() {
        doSaveProfile(FB_ID, testProfileAttributes());
        doPutFriends(FB_ID, Arrays.asList(FRIEND_FB_ID));

        List<String> friends = doGetFriends(FB_ID);

        assertThat(friends.size()).isEqualTo(1);
        assertThat(friends.get(0)).isEqualTo(FRIEND_FB_ID);
    }

    @Test
    public void postAction() {
        doSaveProfile(FB_ID, testProfileAttributes());
        doPostAction(FB_ID, testAction());
    }

    private void doSaveProfile(String id, Attributes attributes) {
        given().contentType("application/json; charset=UTF-8").body(attributes)
                .expect().statusCode(200)
                .when().put("/api/profiles/" + id);
    }

    private void doPutFriends(String id, List<String> friendIds) {
        given().contentType("application/json; charset=UTF-8").body(friendIds)
                .expect().statusCode(200)
                .when().put("/api/profiles/" + id + "/friends");
    }

    private void doPostAction(String id, Action action) {
        given().contentType("application/json; charset=UTF-8").body(action)
                .expect().statusCode(200)
                .when().post("/api/profiles/" + id + "/actions");
    }

    private List<String> doGetFriends(String id) {
        return expect().statusCode(200)
                .when().get("/api/profiles/" + id + "/friends").as(List.class);
    }
}
