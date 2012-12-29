package com.github.aprestaux.funreco.web.controller;

import javax.inject.Inject;

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

		model.addAttribute("email", email);
		model.addAttribute("facebookId", id);

		model.addAttribute("profile", facade.findProfile(email, id));
		// model.addAttribute("profile",null);
		model.addAttribute("actions", facade.findActions(0, 10));

		return "index";
	}

}