package com.lesstif.jira.issue;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import com.lesstif.jira.project.Project;

@Data
@JsonIgnoreProperties({"lastViewed", "aggregateprogress"
,"timeoriginalestimate", "aggregatetimespent",
"fileList"
})
@JsonInclude(value= JsonInclude.Include.NON_EMPTY, content= JsonInclude.Include.NON_NULL)
@Accessors(chain=true)
public class IssueFields {
	private Project  project;
	private String summary;
	
	private Map<String, String> progress;
	
	private TimeTracking timetracking;
	private IssueType issuetype;
	
	private String timespent;
	
	private Reporter reporter;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime created;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime updated;
	
	private String description;
	
	private Priority priority;
	
	private String[] issuelinks;
	
	// for custom field	
	private Map<String,Object> customfield = new HashMap<String, Object>();
	
	@JsonAnyGetter
	public Map<String,Object> getCustomfield(){
		return this.customfield; 
	}
	
	@JsonAnySetter
	public IssueFields setCustomfield(String key,Object value){ 
		if (this.customfield == null)
			this.customfield = new HashMap<String, Object>();
		
		this.customfield.put(key, value);
		
		return this;
	}
		
	private String[] subtasks;
	private Status status;
	
	private String[] labels;

	private Double workratio;

	private String environment;
	
	private List<Component> components;
	
	private Comment comment;
	
	private Vote votes;
	
	private Resolution resolution;
	
	private String[] fixVersions;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime resolutiondate;
	
	private Reporter creator;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime aggregatetimeoriginalestimate;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime duedate;
	
	private Map<String, String> watches;

	private Worklog worklog;
	
	private Reporter assignee;
	
	private List<Attachment> attachment;
	
	private	List<File>	fileList;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime aggregatetimeestimate;
	
	private List<Version> versions;
	
	private Integer timeestimate;
	
	// Helper method
	public IssueFields setProjectId(String id) {
		if (project == null)
			project = new Project();
		
		project.setId(id);
		
		return this;
	}
	
	public IssueFields setProjectKey(String key) {
		if (project == null)
			project = new Project();
		
		project.setKey(key);
		
		return this;
	}
	
	public IssueFields setIssueTypeId(String id) {
		if (issuetype == null)
			issuetype = new IssueType();
		issuetype.setId(id);
		
		return this;
	}
	
	public IssueFields setIssueTypeName(String name) {
		if (issuetype == null)
			issuetype = new IssueType();
		issuetype.setName(name);
		
		return this;
	}
	
	public IssueFields setAssigneeName(String name) {
		if (assignee == null)
			assignee = new Reporter();

		assignee.setName(name);
		
		return this;
	}
	
	public IssueFields setReporterName(String name) {
		if (reporter == null)
			reporter = new Reporter();

		reporter.setName(name);
		
		return this;
	}

	public IssueFields setPriorityId(String id) {
		if (priority == null)
			priority = new Priority();

		priority.setId(id);
		
		return this;		
	}
	
	public IssueFields setPriorityName(String name) {
		if (priority == null)
			priority = new Priority();

		priority.setName(name);
		
		return this;
	}

	public IssueFields addAttachment(String filePath) {
		addAttachment(new File(filePath));

		return this;
	}

	public IssueFields addAttachment(File file) {			
		if (this.fileList == null)
			this.fileList = new ArrayList<File>();
			
		fileList.add(file);
		
		return this;
	}
	
	public IssueFields setComponentsByStringArray(String[] componenArray) {
	    
	    if (componenArray == null || componenArray.length == 0)
	        return this;
	    
	    Component[] c = new Component[componenArray.length];
	    
	    for(int i = 0; i < componenArray.length; i++) {
	        c[i] = new Component(componenArray[i]);
	    }
	    
	    this.components = Arrays.asList(c);
	    
	    return this;
	}
	
}
