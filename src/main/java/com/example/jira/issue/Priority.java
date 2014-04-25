package com.example.jira.issue;

import lombok.Data;

@Data
public class Priority {
	public static final String PRIORITY_BLOCKER = "Blocker";
	public static final String PRIORITY_CRITICAL = "Critical";
	public static final String PRIORITY_MAJOR = "Major";
	public static final String PRIORITY_MINOR = "Minor";
	public static final String PRIORITY_TRIVIAL = "Trivial";
	
	private String self;
	private String iconUrl;
	private String name;
	private String id;
	private String statusColor;
	private String description;
}
