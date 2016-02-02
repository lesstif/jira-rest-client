package com.lesstif.jira.issue;

import com.lesstif.jira.JsonPrettyString;

import lombok.Data;

@Data
public class IssueType extends JsonPrettyString {
	// Default issue type
	public static final String ISSUE_TYPE_BUG = "Bug";
	public static final String ISSUE_TYPE_TASK = "Task";
	public static final String ISSUE_TYPE_IMPROVEMENT = "Improvement";
	public static final String ISSUE_TYPE_SUBTASK = "SubTask";
	public static final String ISSUE_TYPE_NEW_FEATURE = "New Feature";
	
	private String self;
	private String id;
	private String description;
	private String iconUrl;
	private String name;
	private Boolean subtask;
	private Integer avatarId;
}
