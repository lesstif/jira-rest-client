package com.example.jira.issue;

import lombok.Data;

@Data
public class Issue {
	private String expand;
	private String id;
	private String self;
	private String key;
	
	private IssueFields fields;
	
}
