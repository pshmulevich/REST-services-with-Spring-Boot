package com.rest.example;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@Value("${application.message:Default Application Message}")
	private String webControllerMessage = "Hello World";

	@GetMapping("/welcomePage")
	public String welcome(Map<String, Object> model) {
		// put some values into the model map to use inside the jsp
		model.put("time", new Date());
		model.put("webControllerMessage", this.webControllerMessage);
		// "welcome" stands for the path to the jsp page "welcome.jsp"
		return "welcome";
	}

}
