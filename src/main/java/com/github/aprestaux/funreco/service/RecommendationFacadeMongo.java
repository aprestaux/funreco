package com.github.aprestaux.funreco.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Attributes;
import com.github.aprestaux.funreco.api.Object;
import com.github.aprestaux.funreco.api.Recommendations;
import com.github.aprestaux.funreco.domain.DBAction;
import com.github.aprestaux.funreco.domain.DBObject;
import com.github.aprestaux.funreco.domain.DBProfile;
import com.google.code.morphia.Datastore;

/**
 * Implementation of RecommendationFacade using mongodb
 */
public class RecommendationFacadeMongo implements RecommendationFacade {
    @Inject
    private Datastore datastore;

    @Override
    public void updateProfile(String id, Attributes attributes) {
        DBProfile dbProfile = findById(id);

        if (dbProfile == null) {
            dbProfile = new DBProfile(id);
        }

        dbProfile.setAttributes(attributes);

        datastore.save(dbProfile);
    }

    @Override
    public Attributes findProfile(String id) throws ProfileNotFoundException {
        return assertFindById(id).getAttributes();
    }

    @Override
    public Attributes findProfile(String email, String id) {
        DBProfile profile = findByEmail(email);

        return profile != null ? profile.getAttributes() : findById(id).getAttributes();
    }

    @Override
    public void updateFriends(String id, List<String> friendIds) throws ProfileNotFoundException {
        DBProfile dbProfile = assertFindById(id);

        if (friendIds == null) {
            friendIds = new ArrayList<String>();
        }

        dbProfile.setFriendsIds(friendIds);

        datastore.save(dbProfile);
    }

    @Override
    public List<String> findFriends(String id) throws ProfileNotFoundException {
        return assertFindById(id).getFriendsIds();
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
        object.getAttributes().putAll(dbObject.getObjectProperties());
        
        return object;
    }

    public DBObject toDBObject(Object object){
        if (object == null) {
            return null;
        }

        DBObject dbObject = new DBObject();
        dbObject.setObjectId(object.getId());
        dbObject.setObjectProperties(object.getAttributes());

        return dbObject;
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
