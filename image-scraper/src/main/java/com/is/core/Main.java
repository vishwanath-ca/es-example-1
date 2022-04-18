package com.is.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.is.service.CrawlService;

/**
 * MainAppClass - Made it as Web/REST compatible so that we can use it in a web
 * application if needed. TODO 1. Security - Authentication/Authorization as per
 * needs. 2. Customization as needed.
 * 
 * @author vishwa
 *
 */
@SpringBootApplication
public class Main {

	@Autowired
	private CrawlService crawlService;

	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);
	}

	// TODO - Need to implement throttle checks for
	// TOTAL_NUMBER_OF_RECORDS_TO_INDEX, BATCH_SIZE - handle extreme cases to avoid
	// memory pressure.
	@EventListener(ApplicationReadyEvent.class)
	private void init() throws Exception {
		// crawlService.dumpDummyData();
	}

}
