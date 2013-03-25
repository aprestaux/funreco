package com.github.aprestaux.funreco.web.controller;

import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.service.ProfileNotFoundException;
import com.github.aprestaux.funreco.service.RecommendationFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

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
        String facebook_id;
        try {
            if (email.length() > 0)  {
                model.addAttribute("profile", facade.findProfile(email, "0"));
                facebook_id = facade.getProfileId(email);
            }else{
                model.addAttribute("profile", facade.findProfile(id));
                facebook_id = id;
            }
            model.addAttribute("facebook_id", facebook_id);
            model.addAttribute("actions", facade.findActions(facebook_id, 0, 10));
        }
        catch (ProfileNotFoundException exception) {
            model.addAttribute("flashMessage", "Profile Not Found");
        }

		return "index";
	}

    @RequestMapping("/makeReco")
    public String pageReco(@RequestParam("facebookId") String id, Model model) throws ProfileNotFoundException {

        try {
            if (id.length() > 0)  {
                model.addAttribute("profile", facade.findProfile(id));
                model.addAttribute("facebook_id", id);
                model.addAttribute("actions", facade.findActions(id, 0, 10));
                model.addAttribute("recommendations", facade.findRecommendations(id));
            }else{
                model.addAttribute("actions", facade.findActions(0, 10));
                model.addAttribute("recommendations", facade.findDefaultRecommendations());
            }
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