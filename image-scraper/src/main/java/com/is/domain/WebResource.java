package com.is.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 
 * @author vishwa
 *
 */
@Document(indexName = "resource")
public class WebResource {
	
	@Id
	private String id;
	private String url;
	private String[] tags;
	private String description;
	
	// Further we can include, type - image/text/file, validity - resource is still reachable or not, date - last validated date, domain - primary domain name etc.,
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}

}
