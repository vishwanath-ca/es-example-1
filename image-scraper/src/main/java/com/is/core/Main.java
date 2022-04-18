package com.is.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.is.domain.WebResource;
import com.is.service.ISService;

/**
 * MainAppClass - Made it as Web/REST compatible so that we can use it in a web application if needed.
 * TODO
 * 1. Security - Authentication/Authorization as per needs.
 * 2. Customization as needed.
 * 
 * @author vishwa
 *
 */
@SpringBootApplication
public class Main {

	@Autowired
	private ISService service;
	
	// TODO - These can be moved to configurations either at start-up time or as dynamic configurations as needed.
	private static int TOTAL_NUMBER_OF_RECORDS_TO_INDEX = 1000000;
	private static int BATCH_SIZE = 1000;

	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);
	}

	
	// TODO - Need to implement throttle checks for TOTAL_NUMBER_OF_RECORDS_TO_INDEX, BATCH_SIZE - handle extreme cases to avoid memory pressure.
	@EventListener(ApplicationReadyEvent.class)
	private void init() {
		// Sample to index a single record
		//String[] tags = { "veg", "drink" };
		// service.create(tags, "www.google.com");
		
		//Only to demonstrate the nature of Async functionality.
		AtomicInteger batchCounter = new AtomicInteger();

		List<WebResource> records = new ArrayList<WebResource>();
		int batchNumber = batchCounter.incrementAndGet();
		
		for (int i = 0; i < TOTAL_NUMBER_OF_RECORDS_TO_INDEX; i++) {
			WebResource r = new WebResource();
			r.setUrl("www.url-here-" + i + ".com");
			records.add(r);
			if (records.size() >= BATCH_SIZE) {
				System.out.println(batchNumber + "| send: " + new Date());
				service.bulkCreateAsync(records, batchNumber);
				
				records = new ArrayList<WebResource>();
				batchNumber = batchCounter.incrementAndGet();
				System.out.println(batchNumber + "| build: " + new Date());
				
			}
		}
		if(records.size() > 0) {
			service.bulkCreateAsync(records, batchNumber);
		}
	}

}
