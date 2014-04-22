package com.example.jira.issue;

import lombok.Data;

import com.example.jira.project.Project;

@Data
public class IssueFields {
	private Project  project;
	private String summary;
	
	private String progress;
}
