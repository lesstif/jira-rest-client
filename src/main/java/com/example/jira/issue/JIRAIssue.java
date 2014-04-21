package com.example.jira.issue;

import lombok.Data;

@Data
public class JIRAIssue {
	private String expand;
	private String id;
	private String self;
	private String key;
}
