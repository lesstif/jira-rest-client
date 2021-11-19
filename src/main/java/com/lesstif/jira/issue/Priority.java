package com.lesstif.jira.issue;

import com.lesstif.jira.JsonPrettyString;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Priority extends JsonPrettyString {
	public static final String PRIORITY_BLOCKER = "Blocker";
	public static final String PRIORITY_CRITICAL = "Critical";
	public static final String PRIORITY_MAJOR = "Major";
	public static final String PRIORITY_MINOR = "Minor";
	public static final String PRIORITY_TRIVIAL = "Trivial";
	
	private String self;
	private String iconUrl;
	private String name;
	private String id;
	private String statusColor;
	private String description;
}
