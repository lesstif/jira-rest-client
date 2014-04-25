package com.example.jira.issue;

import lombok.Data;

@Data
public class Comment {
	private Integer startAt;
	private Integer maxResults;
	private Integer total;
	private String[] comments;
}
