package com.rest.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByeController {

	@RequestMapping("/bye")
	public String index() {
		return "Bye from Spring Boot!";
	}

}