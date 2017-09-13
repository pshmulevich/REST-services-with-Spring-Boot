package com.rest.example;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
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
public class ControllerSubstringTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testPage1() throws Exception {
		// Matcher that handles finding a substring of a given string
		Matcher<? super String> matcher = new Contains("page 1");
		String name = "applicationTitle";
		String value = "app";
		String queryParams = "?" + name + "=" + value;
		// Same as the original unit test
		mvc.perform(MockMvcRequestBuilders.get("/page1" + queryParams).accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().string(matcher));
	}
}