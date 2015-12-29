package com.lesstif.jira.issue;

import com.lesstif.jira.project.AvatarUrl;

import lombok.Data;

@Data
public class Lead {
	private String self;
	private String name;
	private AvatarUrl avatarUrls;
	private String displayName;
	private Boolean active;
	private String key;
}
