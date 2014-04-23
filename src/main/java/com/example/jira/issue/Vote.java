package com.example.jira.issue;

import lombok.Data;

@Data
public class Vote {
	private String self;
	private int votes;
	private boolean hasVoted;
}
