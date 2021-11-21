package com.lesstif.jira.issue;

import com.lesstif.jira.project.AvatarUrl;

import lombok.Data;

@Data
public class Reporter {	
	private String self;
	private String key;
	private String name;
	private String emailAddress;
	private AvatarUrl avatarUrls;
	private String displayName;
	private Boolean active;
	private String timeZone;
}
