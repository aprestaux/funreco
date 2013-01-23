package com.github.aprestaux.funreco.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Attributes;
import com.github.aprestaux.funreco.api.Recommendations;

/**
 * Stub implementation for RecommendationFacade
 */
public class RecommandationFacadeStub implements RecommendationFacade {
    private Map<String, Attributes> profiles = new HashMap<String, Attributes>();

    private Map<String, List<String>> friends = new HashMap<String, List<String>>();

    public void updateProfile(String id, Attributes attributes) {
        profiles.put(id, attributes);
    }

    public Attributes findProfile(String id) throws ProfileNotFoundException {
        if (!profiles.containsKey(id)) {
            throw new ProfileNotFoundException();
        }

        return profiles.get(id);
    }

    public Attributes findProfile(String email, String id) throws ProfileNotFoundException {
        return findProfile(id);
    }

    public void updateFriends(String id, List<String> friendIds) throws ProfileNotFoundException {
        friends.put(id, friendIds);
    }

    public List<String> findFriends(String id) throws ProfileNotFoundException {
        if (!friends.containsKey(id)) {
            throw new ProfileNotFoundException();
        }

        return friends.get(id);
    }

    public void pushAction(String id, Action action) throws ProfileNotFoundException {

    }

    public List<Action> findActions(int offset, int limit) {
        return null;
    }

    public List<Action> findActions(String id, int offset, int limit) {
        return null;
    }

    public int countActions() {
        return 0;
    }

    public Recommendations findDefaultRecommendations() {
        return null;
    }

    public Recommendations findRecommendations(String id) {
        return null;
    }

	@Override
	public List<Action> findAllActions() {
		// TODO Auto-generated method stub
		return null;
	}
}
