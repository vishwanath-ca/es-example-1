package com.is.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.is.domain.WebResource;
import com.its.pojo.SerpResponse;

import serpapi.GoogleSearch;
import serpapi.SerpApiSearchException;

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
	public void startCrawling(String provider, Integer maxPages) throws Exception {

		// Only to demonstrate the nature of Async functionality.
		AtomicInteger batchCounter = new AtomicInteger();

		// Always trigger for the first time and continue as long as we get records.
		// Abort otherwise.
		boolean hasMoreResults = true;

		int page = 0;

		Map<String, String> parameter = new HashMap<>();
		parameter.put("api_key", "918aa4f9d5d4a9a2028ef00523b38c60b1ab4fcdb2f38d1b24d2a6a186a15ecd"); // TODO - Make it
																										// configurable
																										// later.
		parameter.put("tbm", "isch"); // To get only images.
		parameter.put("q", "south indian food images");


		while (hasMoreResults && page < maxPages) {
			parameter.put("ijn", String.valueOf(page));

			final List<WebResource> records = new ArrayList<WebResource>();
			int batchNumber = batchCounter.incrementAndGet();
			System.out.println(batchNumber + "| build: " + new Date());

			SerpResponse response = null;
			try {
				GoogleSearch search = new GoogleSearch(parameter);
				response = new Gson().fromJson(search.getJson(), SerpResponse.class);
				if (response != null) {
					if (CollectionUtils.isEmpty(response.getImages_results())) {
						response.getImages_results().stream().forEach(image -> {
							WebResource r = new WebResource();
							r.setUrl(image.getThumbnail());
							records.add(r);
						});
					} else {
						// We're done.
						hasMoreResults = false;
					}
				}
				System.out.println(batchNumber + "| sending: " + new Date());
				service.bulkCreateAsync(records, batchNumber);
				System.out.println(batchNumber + "| sent: " + new Date());

				batchNumber = batchCounter.incrementAndGet();

			} catch (SerpApiSearchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			page++;
		}

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
