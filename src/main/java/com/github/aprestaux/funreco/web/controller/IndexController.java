package com.github.aprestaux.funreco.web.controller;

import javax.inject.Inject;

import com.github.aprestaux.funreco.domain.DBProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.aprestaux.funreco.service.ProfileNotFoundException;
import com.github.aprestaux.funreco.service.RecommendationFacade;

@Controller
public class IndexController {

	@Inject
	private RecommendationFacade facade;

	@RequestMapping({ "/", "index.html" })
	public String page(Model model) {
		model.addAttribute("actions", facade.findActions(0, 10));

		return "index";
	}

	@RequestMapping("/searchProfile")
	public String pageProfile(@RequestParam("email") String email,
			@RequestParam("facebookId") String id, Model model) throws ProfileNotFoundException {

        try {
            if (email.length() > 0)  {
                model.addAttribute("profile", facade.findProfile(email, "0"));
            }else{
                model.addAttribute("profile", facade.findProfile(id));
            }
            model.addAttribute("profile_id", id);
            model.addAttribute("actions", facade.findActions(id, 0, 10));
        }
        catch (ProfileNotFoundException exception) {
            model.addAttribute("flashMessage", "Profile Not Found");
        }

		return "index";
	}

	@RequestMapping("/login")
	public String login() {
	    return "login";
	}


}