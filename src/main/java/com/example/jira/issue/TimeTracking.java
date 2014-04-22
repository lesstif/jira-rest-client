package com.example.jira.issue;

import lombok.Data;

@Data
public class TimeTracking {
	private String originalEstimate;
	private String remainingEstimate;
	private String timeSpent;
	private String originalEstimateSeconds;
	private String remainingEstimateSeconds;
	private String timeSpentSeconds;
}
