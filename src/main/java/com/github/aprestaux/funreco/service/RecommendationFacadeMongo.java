package com.github.aprestaux.funreco.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Attributes;
import com.github.aprestaux.funreco.api.Object;
import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.api.Recommendation;
import com.github.aprestaux.funreco.api.Recommendations;
import com.github.aprestaux.funreco.api.RecommendedObject;
import com.github.aprestaux.funreco.domain.DBAction;
import com.github.aprestaux.funreco.domain.DBObject;
import com.github.aprestaux.funreco.domain.DBProfile;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;

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
    public Attributes findProfile(String email, String id) throws ProfileNotFoundException {
        DBProfile profile = findByEmail(email);

        if (profile == null) {
            throw new ProfileNotFoundException("No profile for email " + email);
        }

        return profile.getAttributes();
    }

    @Override
    public String getProfileId(String email) throws ProfileNotFoundException {
        DBProfile profile = findByEmail(email);

        if (profile == null) {
            throw new ProfileNotFoundException("No profile for email " + email);
        }

        return profile.getExternalId();
    }

    @Override
    public void updateFriends(String id, List<String> friendIds)
            throws ProfileNotFoundException {
        DBProfile dbProfile = assertFindById(id);

        if (friendIds == null) {
            friendIds = new ArrayList<String>();
        }

        dbProfile.setFriendsIds(friendIds);

        datastore.save(dbProfile);
    }

    private String jsonIdToString(String jsonString){
    	try {
			jsonString = jsonString.split(":")[1];
		} catch (Exception e) {
			e.printStackTrace();
			return jsonString;
		}
		return jsonString.substring(jsonString.indexOf("\"")+1,jsonString.lastIndexOf("\""));
    }
    
    @Override
    public List<String> findFriends(String id) throws ProfileNotFoundException {
        return assertFindById(id).getFriendsIds();
    }

    @Override
    public void pushAction(String id, Action action)
            throws ProfileNotFoundException {
        DBProfile dbProfile = assertFindById(id);

        DBAction dbAction = new DBAction();
        dbAction.setProfile(dbProfile);
        dbAction.setDate(action.getDate() == null ? new Date() : action.getDate());
        dbAction.setObject(DBObject.fromObject(action.getObject()));

        datastore.save(dbAction);
    }

    @Override
    public List<Action> findActions(int offset, int limit) {
        List<DBAction> dbActions = datastore.find(DBAction.class)
                .offset(offset).limit(limit).asList();

        return toActions(dbActions);
    }

    @Override
    public List<Action> findAllActions() {
        return toActions(allActions());
    }

    @Override
    public List<Action> findActions(String id, int offset, int limit) {
        List<DBAction> dbActions = datastore.find(DBAction.class)
                .filter("profile.externalId", id).offset(offset).limit(limit)
                .asList();

        return toActions(dbActions);
    }

    @Override
    public int countActions() {
        return (int) datastore.find(DBAction.class).countAll();
    }

    @Override
    public Recommendations findDefaultRecommendations() {
    	 List<DBAction> dbActions = new ArrayList<DBAction>();
    	 Query<DBAction> q=datastore.find(DBAction.class); 
         for (DBAction dbAction : q) {
        	 dbActions.add(dbAction);  	 
         }
    	return toRecommendations(dbActions);
    }

    private static List<String> pickNRandom(List<String> lst, int n) {
        ArrayList<String> copy = new ArrayList<String>(lst);
        Collections.shuffle(copy);
        return copy.subList(0, n);
    }
    
    @Override
    public Recommendations findRecommendations(String id) {
        List<String> friendsIds = getFriendsIdsFor(id);
        List<DBAction> dbActions = new ArrayList<DBAction>();
       
        try {
        	friendsIds = pickNRandom(friendsIds, 20);//TODO need to be removed after beam search-like algorithme is set up
        	boolean sufficientActions = false;
			for (String friendId : friendsIds) {
				if(sufficientActions){
					break;
				}
			    for (DBAction dbAction_tmp : allActionsOfProfile(jsonIdToString(friendId))) {
			        dbActions.add(dbAction_tmp);
			        if(dbActions.size()>=11){
			        	sufficientActions = true;
			        	break;
			        }
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			return findDefaultRecommendations();
		}
        Recommendations reco = toRecommendations(dbActions);
        reco.setProfileId(friendsIds.get(0));
        return reco;
    }

    @Override
    public Recommendations findRecommendationsByProperties(String... properties) {
        List<DBAction> result = new ArrayList<DBAction>();
        Query<DBAction> q=datastore.find(DBAction.class);     
        for (DBAction dbAction : q){
            if (dbAction.getObject().containsValue(properties) && !result.contains(dbAction)) {
                result.add(dbAction);
            }
        }

        return toRecommendations(result);
    }

    @Override
    public Recommendations findRecommendationsNotConsumed(String id) {
        List<String> friendsIds = getFriendsIdsFor(id);
        List<DBAction> dbActions = new ArrayList<DBAction>();
        for (String friendId : friendsIds) {
             for (DBAction dbAction_tmp :allActionsOfProfile(friendId)){
                for (DBAction actionOfProfile :allActionsOfProfile(id)) {
                    if (!actionOfProfile.getObject().getObjectId().equals(dbAction_tmp.getObject().getObjectId()))
                        dbActions.add(dbAction_tmp);
                }
            }
        }
        return toRecommendations(dbActions);
    }
    
    public Recommendations toRecommendations(List<DBAction> dbActions) {
        Map<String, RecommendedObject> recommendedObjects = new HashMap<String, RecommendedObject>();
        for (DBAction dbAction : dbActions) {
            Object object = toObject(dbAction.getObject());
            String recomendorId = dbAction.getProfile().getExternalId();
            String recomendorEmail = dbAction.getProfile().getEmail();
            if (recommendedObjects.containsKey(object.getId())) {
                recommendedObjects.get(object.getId()).getBy().add(recomendorId);
                if(!recommendedObjects.get(object.getId()).getByEmail().contains(recomendorEmail))
                recommendedObjects.get(object.getId()).getByEmail().add(recomendorEmail);
            } else {
                List<String> initialRecommenderList = new ArrayList<String>();
                List<String> initialRecommenderListEmail = new ArrayList<String>();
                initialRecommenderList.add(recomendorId);
                initialRecommenderListEmail.add(recomendorEmail);
                RecommendedObject recommendedObject = new RecommendedObject();
                recommendedObject.setBy(initialRecommenderList);
                recommendedObject.setByEmail(initialRecommenderListEmail);
                recommendedObject.setObject(object);
                if(!object.getAttributes().isEmpty() && !object.getId().equals(null) && !object.getId().equals(""))
                recommendedObjects.put(object.getId(), recommendedObject);
            }
        }

        Recommendation recommendation = new Recommendation();
        recommendation.setQuery("");
        ArrayList<RecommendedObject> recommendedObjectList = new ArrayList<RecommendedObject>();
        recommendation.setObjects(recommendedObjectList);
        for (RecommendedObject recoObject : recommendedObjects.values()) {
            recommendation.addObject(recoObject);
        }

        Recommendations recommendations = new Recommendations();
        recommendations.addRecommendation(recommendation);

        return recommendations;
    }

    @Override
    public List<RecommendedObject> getRecommendedObjects(Recommendations recos,int limit){
    	
    	List<RecommendedObject> recommendedObjects = new ArrayList<RecommendedObject>(); 
    	
    	Recommendation recommendation = recos.getEntries().iterator().next();
    	
    	Iterator<RecommendedObject> iterator = recommendation.getObjects().iterator();
    	
    	while (recommendedObjects.size() < limit){
    			recommendedObjects.add(iterator.next());
    	}
    	
    	return recommendedObjects;    	
    }
    public List<Action> toActions(List<DBAction> dbActions) {
        List<Action> actions = new ArrayList<Action>();

        for (DBAction dbAction : dbActions) {
            actions.add(toAction(dbAction));
        }

        return actions;
    }

    public Profile toProfile(DBProfile dbProfile) {
        Profile profile = new Profile();

        profile.setEmail(dbProfile.getEmail());

        return profile;

    }

    public Action toAction(DBAction dbAction) {
        Action action = new Action();

        action.setDate(dbAction.getDate());
        action.setObject(toObject(dbAction.getObject()));
        if (dbAction.getProfile() != null){
            action.setProfile(toProfile(dbAction.getProfile()));
        }

        return action;
    }

    public Object toObject(DBObject dbObject) {
        if (dbObject == null) {
            return null;
        }

        return dbObject.toObject();
    }

    private List<String> getFriendsIdsFor(String id) {
        DBProfile dbProfile = null;
        try {
            dbProfile = assertFindById(id);
        } catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return dbProfile.getFriendsIds();
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
        return datastore.find(DBProfile.class).filter("attributes.email", email).get();
    }

    private DBObject findByObjectId(String id) {
        return datastore.find(DBObject.class).filter("id", id).get();
    }

    private List<DBAction> allActions() {
    	
        return datastore.find(DBAction.class).asList();
    }
    private Query<DBAction> allActionsOfProfile(String id){
    	return  datastore.find(DBAction.class).filter("profile.externalId", id);//.filter("profile.externalId", id);   
    }
    
   

}
