package com.is.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;

import com.is.domain.WebResource;

/**
 * 
 * @author vishwa
 *
 */
public class CrawlService {

	// TODO - These can be moved to configurations either at start-up time or as
	// dynamic configurations as needed.
	private static int TOTAL_NUMBER_OF_RECORDS_TO_INDEX = 1000000;
	private static int BATCH_SIZE = 1000;

	@Autowired
	private ISService service;

	/*
	 * Steps: 1. Trigger crawler 2. Send urls in bulk to index. 3. Continue the
	 * crawler as long as records exists.
	 * 
	 * TODO 1. Handle abort cases 2. Support multiple providers like Google, Yahoo.
	 */
	public void startCrawling(String provider) throws Exception {

	}

	public void dumpDummyData() throws Exception {
		// Sample to index a single record
		// String[] tags = { "veg", "drink" };
		// service.create(tags, "www.google.com");

		// Only to demonstrate the nature of Async functionality.
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
		if (records.size() > 0) {
			service.bulkCreateAsync(records, batchNumber);
		}
	}
}
