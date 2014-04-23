package com.example.jira.issue;

import com.example.jira.project.AvatarUrl;

import lombok.Data;

@Data
public class Creator {
	private String self;
	private String name;
	private String emailAddress;
	private AvatarUrl avatarUrls;
	private String displayName;
	private boolean active;
}
