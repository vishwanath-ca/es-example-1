package com.is.service;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.is.domain.WebResource;
import com.is.repo.WebResourceRepo;

/**
 * batchNumber wherever mentioned is only for demonstrate/debug purposes.
 * 
 * @author vishwa
 *
 */
@Service
public class ISService {
	
	// Using springdata
	@Autowired
	private WebResourceRepo resourceRepo;

	// Using ESClient
	@Autowired
	private RestHighLevelClient client;

	
	public long getDocCounters() {
		return resourceRepo.count();
	}
	
	//Spring asynchronous processing
	@Async
	public void bulkCreateAsync(List<WebResource> records, int batchNumber) {
		bulkCreate(records, batchNumber);
	}

	/*
	 * TODO
	 * 1. index, type can be parameterized
	 * 2. Request Options can be customized using builder.
	 * 3. Fail/Retry mechanism can be implemented whenever a bulk request has failed - using RetryListner or retry call with our own handling.
	 */
	// ESClient asynchronous processing
	public void bulkCreate(List<WebResource> records, int batchNumber) {
		BulkRequest bulkRequest = new BulkRequest();
		for (WebResource resource : records) {
			IndexRequest r = new IndexRequest("resource");
			Map<String, Object> map = new ObjectMapper().convertValue(resource, Map.class);
			r.source(map);
			bulkRequest.add(r);
		}
		RequestOptions options = RequestOptions.DEFAULT;
		client.bulkAsync(bulkRequest, options, new ActionListener<BulkResponse>() {
			@Override
			public void onResponse(BulkResponse response) {
				// Only for demo.
				System.out.println("Batch: "+ batchNumber + " | took: " + response.getTook() + " | failures: " + response.hasFailures());
			}

			@Override
			public void onFailure(Exception e) {
				// Only for demo.
				System.out.println("Error: " + e.getMessage());
				//e.printStackTrace();
			}
		});
	}
	
	// All the below methods are for record level operations using Spring Data.
	public void save(WebResource wr) {
		resourceRepo.save(wr);
	}

	public void create(String[] tags, String url) {
		WebResource wr = new WebResource();
		wr.setTags(tags);
		wr.setUrl(url);
		resourceRepo.save(wr);
	}

	@Async
	public void create(String url) {
		create(null, url);
	}
}
