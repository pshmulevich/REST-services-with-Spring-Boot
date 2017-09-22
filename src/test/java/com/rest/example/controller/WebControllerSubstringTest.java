package com.rest.example.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for testing a web controller that uses a jsp page as view. See this class as an original example
 * https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-web-jsp/src/test/java/sample/jsp/SampleWebJspApplicationTests.java
 *
 * Note: this test is using TestRestTemplate
 */
@RunWith(SpringRunner.class)
// web environment is important to make sure TestRestTemplate bean is available for injection
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class WebControllerSubstringTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testWelcomeJspPage() throws Exception {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/welcomePage", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("Custom Application Message");
	}
}