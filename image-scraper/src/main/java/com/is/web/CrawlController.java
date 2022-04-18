package com.is.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.is.service.CrawlService;

/**
 * 
 * @author vishwa
 *
 */
@RestController
public class CrawlController {

	private UUID secret;

	@Autowired
	private CrawlService crawlService;

	// TODO - provider, later ex: Google, Yahoo etc.,
	@GetMapping(path = "crawl/prepare")
	public String prepare() {
		secret = UUID.randomUUID();
		return secret.toString();
	}

	/*
	 * TODO 1. provider, later ex: Google, Yahoo etc., 2. Handle request level
	 * status, perist who/when triggered it and how many records were scraped/stored
	 * etc., 3. Abort the indexing process.
	 */
	@GetMapping(path = "crawl/init")
	public String init(@RequestParam(value = "key") String key,
			@RequestParam(value = "provider", required = false) String provider) throws Exception {
		if (secret != null && secret.toString().equals(key)) {
			crawlService.dumpDummyData();
			return "Alright, process is starting now.";

		} else {
			return "Nice try! But Sorry!!!";
		}
	}

}
