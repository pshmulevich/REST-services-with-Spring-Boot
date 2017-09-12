package com.rest.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rest.example.services.DateService;

@RestController
public class ControllerForRestApplication {

	// Inject DateService
	@Autowired
	private DateService dateService;

	@RequestMapping("/")
	public String index() {
		return "Welcome to Spring Application with Boot. It is " + dateService.getDate();
	}

	@RequestMapping("/page1")
	public String page1(@RequestParam(value = "applicationTitle") String applicationTitle) {
		return "Welcome to page 1 of " + applicationTitle;
	}

	@RequestMapping("/page2")
	public String page2() {
		return "Welcome to page 2";
	}

	@RequestMapping("/welcomePage")
	public ModelAndView welcomePage() {

		// return back to welcome.jsp
		ModelAndView model = new ModelAndView("welcome");

		return model;

	}

}