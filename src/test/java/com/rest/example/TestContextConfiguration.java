package com.rest.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * This class is needed to create a SpringBoot application configuration for the tests.
 * Particularly, it allows tests to access the application.properties file
 *
 */
@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
public class TestContextConfiguration {

}
