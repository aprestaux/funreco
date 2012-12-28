package com.github.aprestaux.funreco.service;

import java.util.List;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Friends;
import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.api.Recommendations;

public interface RecommendationFacade {
    public void updateProfile(Profile profile);

    public Profile findProfile(String id) throws ProfileNotFoundException;

    public Profile findProfile(String email, String id) throws ProfileNotFoundException;

    public void updateFriends(String id, Friends friends) throws ProfileNotFoundException;

    public Friends findFriends(String id) throws ProfileNotFoundException;

    public void pushAction(String id, Action action) throws ProfileNotFoundException;

    public List<Action> findActions(int offset, int limit);

    public List<Action> findActions(String id, int offset, int limit);

    public int countActions();

    public Recommendations findDefaultRecommendations();

    public Recommendations findRecommendations(String id);
}
