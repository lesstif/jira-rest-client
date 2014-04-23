package com.example.jira.issue;

import org.joda.time.DateTime;

import lombok.Data;

@Data
public class Version {
	private String self;
	private String id;
	private String description;
	private String name;
	private boolean archived;
	private boolean released;
	private DateTime releaseDate;
	private boolean overdue;
	private DateTime userReleaseDate;
	private String projectId;	 
}
