package com.github.aprestaux.funreco.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Friend;
import com.github.aprestaux.funreco.api.Friends;
import com.github.aprestaux.funreco.api.Object;
import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.api.Recommendations;
import com.github.aprestaux.funreco.domain.DBAction;
import com.github.aprestaux.funreco.domain.DBObject;
import com.github.aprestaux.funreco.domain.DBProfile;
import com.google.code.morphia.Datastore;

@Service
public class RecommendationFacadeImpl implements RecommendationFacade {
    @Inject
    private Datastore datastore;

    @Override
    public void updateProfile(Profile profile) {
        DBProfile dbProfile = findById(profile.getId());

        if (dbProfile == null) {
            dbProfile = new DBProfile(profile.getId());
        }

        dbProfile.setName(profile.getName());
        dbProfile.setEmail(profile.getEmail());

        datastore.save(dbProfile);
    }

    @Override
    public Profile findProfile(String id) throws ProfileNotFoundException {
        return toProfile(assertFindById(id));
    }

    @Override
    public Profile findProfile(String email, String id) {
        DBProfile profile = findByEmail(email);

        return profile != null ? toProfile(profile) : toProfile(findById(id));
    }

    @Override
    public void updateFriends(String id, Friends friends) throws ProfileNotFoundException {
        DBProfile dbProfile = assertFindById(id);

        if (friends == null) {
            friends = new Friends();
        }

        List<String> ids = new ArrayList<String>();

        for (Friend friend : friends) {
            ids.add(friend.getId());
        }

        dbProfile.setFriendsIds(ids);

        datastore.save(dbProfile);
    }

    @Override
    public Friends findFriends(String id) throws ProfileNotFoundException {
        DBProfile dbProfile = assertFindById(id);

        Friends friends = new Friends();

        if (dbProfile.getFriendsIds() != null) {
            for (String friendId : dbProfile.getFriendsIds()) {
                friends.add(toFriend(friendId));
            }
        }

        return friends;
    }

    @Override
    public void pushAction(String id, Action action) throws ProfileNotFoundException {
        DBProfile dbProfile = assertFindById(id);

        DBAction dbAction = new DBAction();
        dbAction.setProfile(dbProfile);
        dbAction.setDate(action.getDate() == null ? new Date() : action.getDate());
        dbAction.setObject(toDBObject(action.getObject()));

        datastore.save(dbAction);
    }

    @Override
    public List<Action> findActions(int offset, int limit) {
        List<DBAction> dbActions = datastore.find(DBAction.class).offset(offset).limit(limit).asList();

        return toActions(dbActions);
    }

    @Override
    public List<Action> findActions(String id, int offset, int limit) {
        List<DBAction> dbActions = datastore.find(DBAction.class).filter("profile.externalId", id).offset(offset).limit(limit).asList();

        return toActions(dbActions);
    }

    @Override
    public int countActions() {
        return (int) datastore.find(DBAction.class).countAll();
    }

    @Override
    public Recommendations findDefaultRecommendations() {
        /*def lastRecommendation = [:]
        def views = 0
        def today= new Date()
        //List<Profile> profiles = []
        List<Action> actions = Action.withCriteria {
            between("date", today - 15, today)
        }

        for (action in actions)
        {
            Object objectTracked = null
            if(action.object){
                objectTracked = Object.findByObjectId(action.object.objectId)
            }
            else{
                throw new UnsupportedOperationException("action doesn't contains object.")
            }

            if(objectTracked){
                List<Action> actionsOfObject = Action.withCriteria {
                    eq("object",objectTracked)
                }
                for (act in actionsOfObject)
                {
                    // profiles.add(act.profile)
                    views++
                }
                lastRecommendation.put(convertIntoAction(action).object,views)
            }
            else{
                throw new UnsupportedOperationException("there is no object with objectId = "+action.object.objectId+"\n Recommendation on this object is impossible!")
            }

        }
        lastRecommendation.entrySet().sort{it.value}.reverse()
        Recommendations defaultRecommendation = new Recommendations(recommendations : lastRecommendation, profile: null)
        return defaultRecommendation*/
        return null;
    }

    @Override
    public Recommendations findRecommendations(String id) {
        /*def lastRecommendation = [:]
        def views = 0
        def today= new Date()
        List<Action> actionsOfFriends
        Profile profile= Profile.findById(id)
        List<Action> actions = Action.findAll()
        for (action in actions)
        {
            Profile friend= action.profile
            if (profile.friendsIds.contains(friend.id))
            {
                actionsOfFriends = action
            }
        }
        for (action in actionsOfFriends)
        {
            List<Action> actionsOfObject = Action.withCriteria {
            eq("object",action.object)
        }
            for (act in actionsOfObject)
            {
                views++
            }
            lastRecommendation.put(action.object,views)

        }
        lastRecommendation.entrySet().sort{it.value}.reverse()
        Recommendations defaultRecommendation = new Recommendations(recommendations : lastRecommendation, date : today)
        return defaultRecommendation */
        return null;
    }

    private Profile toProfile(DBProfile dbProfile) {
        if (dbProfile == null) {
            return null;
        }

        Profile profile = new Profile(dbProfile.getExternalId());

        profile.setEmail(dbProfile.getEmail());
        profile.setName(dbProfile.getName());
        
        return profile;
    }

    public List<Action> toActions(List<DBAction> dbActions){
        List<Action> actions = new ArrayList<Action>();

        for(DBAction dbAction : dbActions) {
            actions.add(toAction(dbAction));
        }
        
        return actions;
    }

    public Action toAction(DBAction dbAction){
        Action action = new Action();

        action.setDate(dbAction.getDate());
        action.setObject(toObject(dbAction.getObject()));
        
        return action;
    }

    public Object toObject(DBObject dbObject){
        if (dbObject == null) {
            return null;
        }

        Object object = new Object();
        object.setId(dbObject.getObjectId());
        object.getProperties().putAll(dbObject.getObjectProperties());
        
        return object;
    }

    public DBObject toDBObject(Object object){
        if (object == null) {
            return null;
        }

        DBObject dbObject = new DBObject();
        dbObject.setObjectId(object.getId());
        dbObject.setObjectProperties(object.getProperties());

        return dbObject;
    }

    public Friend toFriend(String id) {
        Friend friend = new Friend();

        friend.setId(id);

        return friend;
    }

    private DBProfile assertFindById(String id) throws ProfileNotFoundException {
        DBProfile dbProfile = findById(id);

        if (dbProfile == null) {
            throw new ProfileNotFoundException("No profile for id " + id);
        }

        return dbProfile;
    }

    private DBProfile findById(String id) {
        return datastore.find(DBProfile.class).filter("externalId", id).get();
    }

    private DBProfile findByEmail(String email) {
        return datastore.find(DBProfile.class).filter("email", email).get();
    }

    private DBObject findByObjectId(String id) {
        return datastore.find(DBObject.class).filter("id", id).get();
    }
}
