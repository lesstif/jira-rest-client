package com.lesstif.jira.issue;

import lombok.Data;

@Data
public class Resolution {
	private String id;
	private String self;
	private String description;
	private String iconUrl;
	private String name;
}
