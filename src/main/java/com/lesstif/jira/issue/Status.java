package com.lesstif.jira.issue;

import lombok.Data;

@Data
public class Status {
	private String self;
	private String description;
	private String iconUrl;
	private String name;
	private String id;
	
	private StatusCategory statusCategory;
}
