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
 * 
 * @author vishwa
 *
 */
@SpringBootApplication
public class Main {

	@Autowired
	private ISService service;

	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void init() {
		// Sample to index a single record
		//String[] tags = { "veg", "drink" };
		// service.create(tags, "www.google.com");
		
		//Only to demonstrate the nature of Async functionality.
		AtomicInteger batchCounter = new AtomicInteger();

		List<WebResource> records = new ArrayList<WebResource>();
		int batchNumber = batchCounter.incrementAndGet();
		
		for (int i = 0; i < 1000000; i++) {
			WebResource r = new WebResource();
			r.setUrl("www.url-here-" + i + ".com");
			records.add(r);
			if (records.size() >= 100) {
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
