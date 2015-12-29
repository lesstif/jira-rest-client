package com.lesstif.jira.project;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

import lombok.Data;

import com.lesstif.jira.JsonPrettyString;
import com.lesstif.jira.issue.Component;
import com.lesstif.jira.issue.IssueType;
import com.lesstif.jira.issue.Lead;
import com.lesstif.jira.issue.Version;


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
	private String url;
	
	private DateTime startDate;
	
	private Lead lead;
	
	private AvatarUrl avatarUrls; 
	private ProjectCategory projectCategory;
	
	private List<Component> components;
	private List<IssueType> issueTypes;
	private List<Version> versions;
}
