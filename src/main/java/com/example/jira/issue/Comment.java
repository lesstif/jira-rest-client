package com.example.jira.issue;

import lombok.Data;

@Data
public class Comment {
	private int startAt;
	private int maxResults;
	private int total;
	private String[] comments;
}
