package com.github.aprestaux.funreco.service;

import java.util.List;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Attributes;
import com.github.aprestaux.funreco.api.Recommendations;

public interface RecommendationFacade {
    public void updateProfile(String id, Attributes attributes);

    public Attributes findProfile(String id) throws ProfileNotFoundException;

    public Attributes findProfile(String email, String id) throws ProfileNotFoundException;

    public void updateFriends(String id, List<String> friendIds) throws ProfileNotFoundException;

    public List<String> findFriends(String id) throws ProfileNotFoundException;

    public void pushAction(String id, Action action) throws ProfileNotFoundException;

    public List<Action> findActions(int offset, int limit);

    public List<Action> findActions(String id, int offset, int limit);

    public int countActions();

    public Recommendations findDefaultRecommendations();

    public Recommendations findRecommendations(String id);

	public List<Action> findAllActions();
}
