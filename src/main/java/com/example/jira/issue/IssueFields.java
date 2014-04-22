package com.example.jira.issue;

import java.util.Map;

import lombok.Data;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.joda.time.DateTime;

import com.example.jira.project.Project;

@Data
public class IssueFields {
	private Project  project;
	private String summary;
	
	private Map<String, String> progress;
	
	private TimeTracking timetracking;
	private IssueType issuetype;
	
	private String timespent;
	
	private Reporter reporter;
	
	private DateTime created;
	private DateTime updated;
	
	private String description;
	
	private Priority priority;
	
	private String[] issuelinks;
	
	// for custom field
	@JsonDeserialize(using=CustomFieldDeSerializer.class)
	private Object data;
	
	private String customfield_11009;
	private String customfield_11008;
	private String customfield_11007;
	private String customfield_11006;
	
	private String[] subtasks;
	private Status status;
}
