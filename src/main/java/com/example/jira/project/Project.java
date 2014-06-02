package com.example.jira.project;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Data;

import com.example.jira.JsonPrettyString;
import com.example.jira.issue.Component;
import com.example.jira.issue.IssueType;
import com.example.jira.issue.Lead;
import com.example.jira.issue.Version;


/**
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/latest/#d2e86">/rest/api/2/project</a>
 * 
 * @author lesstif
 *
 */
@Data
@JsonIgnoreProperties({"assigneeType", "roles"
})
public class Project extends JsonPrettyString{
	private String expand;
	private String self;
	
	private String id;
	private String key;
	
	private String description;
	private String name;
	
	private Lead lead;
	
	private AvatarUrl avatarUrls; 
	private ProjectCategory projectCategory;
	
	private List<Component> components;
	private List<IssueType> issueTypes;
	private List<Version> versions;
}
