package com.example.jira.issue;

import java.util.Map;

import org.joda.time.DateTime;

import com.example.jira.JsonPrettyString;

import lombok.Data;

@Data
public class Worklog extends JsonPrettyString{
	
	private String self;
	private String id;
	private Reporter author;
	private Reporter updateAuthor;
	private String comment;
	private Map<String, String> visibility;
	private DateTime started;
	private String timeSpent;
	private Integer timeSpentSeconds;
	
	private Integer startAt;
	private Integer maxResults;
	private Integer total;
	private String[] worklogs;
}
