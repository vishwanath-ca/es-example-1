package com.is.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is.service.ISService;

/**
 * TODO
 * 1. Security, Authentication, Authorization
 * 2. Customizations
 * 
 * @author vishwa
 *
 */
@RestController
public class DocumentController {

	@Autowired
	private ISService service;
	
	/*
	 * TODO
	 * 1. Parameterize doc index etc.,
	 */
	@GetMapping(path = "doc/counters")
	public String counters() {
		return "Total: " + service.getDocCounters();
	}


}
