package com.is.service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author vishwa
 *
 */
public class CrawlService {
	
	@Autowired
	private ISService service;
	
	/*
	 * Steps:
	 * 1. Trigger crawler
	 * 2. Send urls in bulk to index.
	 * 3. Continue the crawler as long as records exists.
	 * 
	 * TODO
	 * 1. Handle abort cases
	 * 2. Support multiple providers like Google, Yahoo.
	 */
	public void startCrawling(String provider) throws Exception {
		
	}
}
