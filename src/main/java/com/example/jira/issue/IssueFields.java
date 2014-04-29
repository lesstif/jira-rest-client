package com.example.jira.issue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.example.jira.project.Project;

@Data
@JsonIgnoreProperties({"lastViewed", "aggregateprogress"
,"timeoriginalestimate", "aggregatetimespent",
"fileList"
})
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
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
	private Map<String,Object> customfield;
	
	@JsonAnyGetter
	public Map<String,Object> getCustomfield(){ 
		return this.customfield; 
	}
	
	@JsonAnySetter
	public void setCustomfield(String key,Object value){ 
		if (this.customfield == null)
			this.customfield = new HashMap<String, Object>();
		
		this.customfield.put(key, value); 
	}
		
	private String[] subtasks;
	private Status status;
	
	private String[] labels;
	
	private Integer workratio;
	
	private String environment;
	
	private List<Component> components;
	
	private Comment comment;
	
	private Vote votes;
	
	private Resolution resolution;
	
	private String[] fixVersions;
	private DateTime resolutiondate;
	
	private Reporter creator;
	
	private DateTime aggregatetimeoriginalestimate;
	private DateTime duedate;
	
	private Map<String, String> watches;
	private Worklog worklog;
	
	private Reporter assignee;
	
	private List<Attachment> attachment;
	
	private	List<File>	fileList;
	
	private DateTime aggregatetimeestimate;
	
	private List<Version> versions;
	
	private Integer timeestimate;
	
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
	
	public void setIssueTypeName(String name) {
		if (issuetype == null)
			issuetype = new IssueType();
		issuetype.setName(name);
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
	
	public void setPriorityName(String name) {
		if (priority == null)
			priority = new Priority();

		priority.setName(name);		
	}

	public void addAttachment(String filePath) {
		addAttachment(new File(filePath));		
	}

	public void addAttachment(File file) {			
		if (this.fileList == null)
			this.fileList = new ArrayList<File>();
			
		fileList.add(file);
	}
}
