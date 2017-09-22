package com.rest.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloseController {

	@RequestMapping("/depart")
	public String index() {
		return "Leaving Application";
	}

}