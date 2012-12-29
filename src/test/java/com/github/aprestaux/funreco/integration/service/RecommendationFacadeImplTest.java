package com.github.aprestaux.funreco.integration.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.github.aprestaux.funreco.IntegrationSpringConfig;
import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Attributes;
import com.github.aprestaux.funreco.domain.DBAction;
import com.github.aprestaux.funreco.domain.DBProfile;
import com.github.aprestaux.funreco.service.ProfileNotFoundException;
import com.github.aprestaux.funreco.service.RecommendationFacade;
import com.github.aprestaux.funreco.service.RecommendationFacadeMongo;
import com.github.aprestaux.funreco.utils.TestData;
import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;

import static com.github.aprestaux.funreco.utils.TestData.*;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = "integration")
public class RecommendationFacadeImplTest {
    @Configuration
    @Import(IntegrationSpringConfig.class)
    static class ContextConfiguration {

    }

    @Inject
    private Datastore datastore;

    @Inject
    private Mongo mongo;

    @Inject
    private RecommendationFacade facade;

    @Before
    public void clean() {
        mongo.dropDatabase(IntegrationSpringConfig.DB_NAME);
    }

    @Test
    public void updateUnknownProfile() {
        // act
        facade.updateProfile(FB_ID, testProfileAttributes());

        // assert
        Attributes attributes = datastore.find(DBProfile.class).get().getAttributes();
        assertThat(attributes).isEqualTo(testProfileAttributes());
    }

    @Test
    public void updateExistingProfile(){
        //arrange
        facade.updateProfile(FB_ID, testProfileAttributes());

        //act
        Attributes attributes = new Attributes().append("mail", "newprofile@test.com").append("name", "newProfile");
        facade.updateProfile(FB_ID, attributes);

        //assert
        Attributes dbAttributes = datastore.find(DBProfile.class).get().getAttributes();
        assertThat(dbAttributes).isEqualTo(attributes);
    }

    @Test
    public void findProfile() throws ProfileNotFoundException {
        // arrange
        facade.updateProfile(FB_ID, testProfileAttributes());

        // act
        Attributes attributes = facade.findProfile(TestData.FB_ID);

        // assert
        assertThat(datastore.find(DBProfile.class).get().getAttributes()).isEqualTo(attributes);
    }

    @Test
    public void updateFriends() throws ProfileNotFoundException {
        //arrange
        facade.updateProfile(FB_ID, testProfileAttributes());

        //act
        facade.updateFriends(TestData.FB_ID, Arrays.asList(FRIEND_FB_ID));

        //assert
        assertThat(datastore.find(DBProfile.class).get().getFriendsIds()).containsExactly(TestData.FRIEND_FB_ID);
    }

    @Test
    public void findFriends() throws ProfileNotFoundException {
        //arrange
        facade.updateProfile(FB_ID, testProfileAttributes());
        facade.updateProfile(FRIEND_FB_ID, testFriendProfileAttributes());
        facade.updateFriends(TestData.FB_ID, Arrays.asList(FRIEND_FB_ID));

        //act
        List<String> friends = facade.findFriends(TestData.FB_ID);

        //assert
        assertThat(friends).hasSize(1);
        assertThat(friends.get(0)).isEqualTo(TestData.FRIEND_FB_ID);
    }

    @Test
    public void pushNewAction() throws ProfileNotFoundException {
        // arrange
        facade.updateProfile(FB_ID, testProfileAttributes());

        // act
        facade.pushAction(TestData.FB_ID, new Action(testObject()));

        // assert
        assertThat(datastore.find(DBAction.class).countAll()).isEqualTo(1);
        assertThat(datastore.find(DBAction.class).get().getProfile().getExternalId()).isEqualTo(TestData.FB_ID);
    }

    @Test
    public void pushActionForSameObject() throws ProfileNotFoundException {
        //arrange
        facade.updateProfile(FB_ID, testProfileAttributes());
        facade.pushAction(TestData.FB_ID, new Action(testObject()));

        //act
        facade.pushAction(TestData.FB_ID, new Action(testObject()));

        //assert
        assertThat(datastore.find(DBAction.class).countAll()).isEqualTo(2);
    }

    @Test
    public void findActions() throws ProfileNotFoundException {
        // arrange
        facade.updateProfile(FB_ID, testProfileAttributes());

        //act
        facade.pushAction(TestData.FB_ID, new Action(testObject()));

        //assert
        assertThat(facade.findActions(0, 5).size()).isEqualTo(1);
        assertThat(facade.findActions(10, 2).size()).isEqualTo(0);
    }

    @Test
    public void findActionsWithId() throws ProfileNotFoundException {
        //arrange
        facade.updateProfile(FB_ID, testProfileAttributes());
        facade.updateProfile(FRIEND_FB_ID, testFriendProfileAttributes());

        //act
        facade.pushAction(TestData.FB_ID, new Action(testObject()));
        facade.pushAction(TestData.FB_ID, new Action(testObject()));
        facade.pushAction(TestData.FRIEND_FB_ID, new Action(testObject()));

        //assert
        assertThat(facade.findActions(TestData.FB_ID, 0, 10).size()).isEqualTo(2);
    }

    @Test
    public void countActions() throws ProfileNotFoundException {
        //arrange
        facade.updateProfile(FB_ID, testProfileAttributes());
        facade.updateProfile(FRIEND_FB_ID, testFriendProfileAttributes());

        //act
        facade.pushAction(TestData.FB_ID, new Action(testObject()));
        facade.pushAction(TestData.FB_ID, new Action(testObject()));
        facade.pushAction(TestData.FRIEND_FB_ID, new Action(testObject()));

        assertThat(facade.countActions()).isEqualTo(3);
    }

    /*
    @Test
    public void findDefaultRecommendations() {
        //arrange (push actions)
        def profile1 = new PublicProfile(facebookId: "fbId1", email: "123@test.com", name: "123")
        def profile2 = new PublicProfile(facebookId: "fbId2", email: "456@test.com", name: "456")
        def publicObject = new PublicObject(id: "publicObjectId", objectProperties: ["show":["musique", "dance"]])
        def action1 = new PublicAction(profile: profile1, object: publicObject, date: new Date())
        def action2 = new PublicAction(profile: profile2, object: publicObject, date: new Date())
        facade.updateProfile(profile1)
        facade.updateProfile(profile2)
        facade.pushObject(publicObject)
        facade.pushAction(action1)
        facade.pushAction(action2)

        //act
        PublicRecommendations reco = facade.findDefaultRecommendations()

        //assert
        assert reco.recommendations.size() > 0
    }   */

    
}
