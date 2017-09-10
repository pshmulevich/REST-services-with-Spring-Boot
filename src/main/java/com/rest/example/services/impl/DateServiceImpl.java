package com.rest.example.services.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.rest.example.services.DateService;

/**
 * DateService implementation.
 * The @Component annotation makes it a Spring bean.
 */
@Component
public class DateServiceImpl implements DateService {

	@Override
	public String getDate() {
		return new Date().toString();
	}

}
