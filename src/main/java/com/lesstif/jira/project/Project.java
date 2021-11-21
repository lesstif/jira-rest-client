package com.lesstif.jira.project;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper=false)
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

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime startDate;
	
	private Lead lead;
	
	private AvatarUrl avatarUrls; 
	private ProjectCategory projectCategory;
	
	private List<Component> components;
	private List<IssueType> issueTypes;
	private List<Version> versions;
	
	private String projectTypeKey;

	private boolean archived;
}
