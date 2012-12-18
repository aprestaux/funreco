package com.github.aprestaux.funreco.web.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Friends;
import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.service.ProfileNotFoundException;
import com.github.aprestaux.funreco.service.RecommendationFacade;

@Controller
public class RestController {
    @Inject
    private RecommendationFacade recommendationFacade;

    @RequestMapping(value = "/api/profiles/{facebookId}", method = RequestMethod.GET)
    @ResponseBody
    public Profile getProfile(@PathVariable String facebookId) throws ProfileNotFoundException {
        return recommendationFacade.findProfile(facebookId);
    }

    @RequestMapping(value = "/api/profiles", method = RequestMethod.POST)
    @ResponseBody
    public void postProfile(@RequestBody Profile profile) {
        recommendationFacade.updateProfile(profile);
    }

    @RequestMapping(value = "/api/profiles/{facebookId}/friends", method = RequestMethod.GET)
    @ResponseBody
    public Friends getFriends(@PathVariable String facebookId) throws ProfileNotFoundException {
        return recommendationFacade.findFriends(facebookId);
    }

    @RequestMapping(value = "/api/profiles/{facebookId}/friends", method = RequestMethod.PUT)
    @ResponseBody
    public void putFriends(@RequestBody Friends friends, @PathVariable String facebookId) throws ProfileNotFoundException {
        recommendationFacade.updateFriends(facebookId, friends);
    }

    @RequestMapping(value = "/api/profiles/{facebookId}/actions", method = RequestMethod.POST)
    @ResponseBody
    public void postAction(@RequestBody Action action) throws ProfileNotFoundException {
        recommendationFacade.pushAction(action);
    }
}
