package com.example.jira.issue;

import lombok.Data;

@Data
public class IssueType {
	private String self;
	private String id;
	private String description;
	private String iconUrl;
	private String name;
	private boolean subtask;
}
