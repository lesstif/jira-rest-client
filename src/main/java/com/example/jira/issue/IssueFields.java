package com.example.jira.issue;

import java.util.List;
import java.util.Map;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.joda.time.DateTime;

import com.example.jira.project.Project;

@Data
@JsonIgnoreProperties({"lastViewed", "aggregateprogress", "worklog"
,"timeoriginalestimate", "aggregatetimespent"
//TODO
,"attachment"
})
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
	private String customfield_11011;
	private String customfield_11010, customfield_10401, customfield_10404, customfield_11100, customfield_10403, customfield_11101, customfield_11003, customfield_11002;
	private String customfield_11005, customfield_11004, customfield_11001, customfield_11000, customfield_10602, customfield_10601, customfield_10600;
	private String customfield_10502, customfield_10501;
	
	private String[] subtasks;
	private Status status;
	
	private String[] labels;
	
	private int workratio;
	
	private String environment;
	
	private String[] components;
	
	private Comment comment;
	
	private Vote votes;
	
	private Resolution resolution;
	
	private String[] fixVersions;
	private DateTime resolutiondate;
	
	private Reporter creator;
	
	private DateTime aggregatetimeoriginalestimate;
	private DateTime duedate;
	
	private Map<String, String> watches;
	private Map<String, String> worklog;
	
	private Reporter assignee;
	
	private Attachment attachment;
	
	private DateTime aggregatetimeestimate;
	
	private List<Version> versions;
	
	private int timeestimate;
	
	// Helper method
	public void setProjectId(String id) {
		if (project == null)
			project = new Project();
		
		project.setId(id);
	}
	
	public void setProjectKey(String key) {
		if (project == null)
			project = new Project();
		
		project.setKey(key);
	}
	
	public void setIssueTypeId(String id) {
		if (issuetype == null)
			issuetype = new IssueType();
		issuetype.setId(id);
	}
	
	public void setAssigneeName(String name) {
		if (assignee == null)
			assignee = new Reporter();

		assignee.setName(name);
	}
	
	public void setReporterName(String name) {
		if (reporter == null)
			reporter = new Reporter();

		reporter.setName(name);
	}

	public void setPriorityId(String id) {
		if (priority == null)
			priority = new Priority();

		priority.setId(id);		
	}
}
