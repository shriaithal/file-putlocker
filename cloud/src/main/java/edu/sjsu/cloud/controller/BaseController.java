package edu.sjsu.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String startPage(ModelMap model) {
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		return "home";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(ModelMap model) {
		return "login";
	}
}
