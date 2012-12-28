package com.github.aprestaux.funreco.web.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.aprestaux.funreco.api.Action;
import com.github.aprestaux.funreco.api.Attributes;
import com.github.aprestaux.funreco.api.Friends;
import com.github.aprestaux.funreco.service.ProfileNotFoundException;
import com.github.aprestaux.funreco.service.RecommendationFacade;

@Controller
public class RestController {
    @Inject
    private RecommendationFacade recommendationFacade;

    @RequestMapping(value = "/api/profiles/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Attributes getProfile(@PathVariable String id) throws ProfileNotFoundException {
        return recommendationFacade.findProfile(id);
    }

    @RequestMapping(value = "/api/profiles/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void postProfile(@PathVariable String id, @RequestBody Attributes attributes) {
        recommendationFacade.updateProfile(id, attributes);
    }

    @RequestMapping(value = "/api/profiles/{id}/friends", method = RequestMethod.GET)
    @ResponseBody
    public Friends getFriends(@PathVariable String id) throws ProfileNotFoundException {
        return recommendationFacade.findFriends(id);
    }

    @RequestMapping(value = "/api/profiles/{id}/friends", method = RequestMethod.PUT)
    @ResponseBody
    public void putFriends(@PathVariable String id, @RequestBody Friends friends) throws ProfileNotFoundException {
        recommendationFacade.updateFriends(id, friends);
    }

    @RequestMapping(value = "/api/profiles/{id}/actions", method = RequestMethod.POST)
    @ResponseBody
    public void postAction(@PathVariable String id, @RequestBody Action action) throws ProfileNotFoundException {
        recommendationFacade.pushAction(id, action);
    }
}
