package com.example.jira.issue;

import lombok.Data;

import org.joda.time.DateTime;

import com.example.jira.JsonPrettyString;

@Data
/**
 * @see https://docs.atlassian.com/jira/REST/latest/#d2e4213
 * 
 * @author lesstif
 *
 */
public class Attachment extends JsonPrettyString{
	private String id;
	private String self;
	private String filename;
	private Reporter author;
	
	private DateTime created;
	
	private Integer size;
	private String mimeType;
	private String content;
	private String thumbnail;	
	
}
