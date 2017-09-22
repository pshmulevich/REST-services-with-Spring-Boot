package com.rest.example.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testFullMatch() throws Exception {
		//
		String paramName1 = "applicationTitle";
		String paramValue1 = "app";
		String queryParams = "?" + paramName1 + "=" + paramValue1;
		String expectedContent = "Welcome to page 1 of " + paramValue1;
		mvc.perform(MockMvcRequestBuilders.get("/page1" + queryParams).accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().string(expectedContent));
	}
}