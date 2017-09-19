package com.rest.example.controller;

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
// web environment is important to make sure TestRestTemplate bean is available for injection
@SpringBootTest
// (webEnvironment = WebEnvironment.RANDOM_PORT)
// @DirtiesContext
@AutoConfigureMockMvc
public class StockControllerTest {

	@Autowired
	private MockMvc mvc;

	// http://localhost:8080/demo/add?symbol=AAPL&date=09/21/2017&previousClose=100&price=100
	@Test
	public void testAddStock() throws Exception {
		// Matcher that handles finding a substring of a given string
		Matcher<? super String> matcher = new Contains("Saved new entry");
		String symbol = "F1";
		String date = "09/09/2017";
		String previousClose = "100";
		String price = "100";
		String queryParams = "?" + "symbol=" + symbol + "&date=" + date + "&previousClose=" + previousClose + "&price=" + price;
		// Same as the original unit test
		mvc.perform(MockMvcRequestBuilders.get("/demo/add" + queryParams).accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().string(matcher));
		testDeleteStock();
	}

	@Test
	public void testDeleteStock() throws Exception {
		// Matcher that handles finding a substring of a given string
		Matcher<? super String> matcher = new Contains("");
		String symbol = "F1";
		String date = "09/09/2017";
		String previousClose = "100";
		String price = "100";
		String queryParams = "?" + "symbol=" + symbol + "&date=" + date + "&previousClose=" + previousClose + "&price=" + price;

		mvc.perform(MockMvcRequestBuilders.get("/demo/delete" + queryParams).accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().string(matcher));
	}
}