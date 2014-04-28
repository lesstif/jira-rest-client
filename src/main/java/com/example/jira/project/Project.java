package com.example.jira.project;
import lombok.Data;

import com.example.jira.JsonPrettyString;


/**
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/latest/#d2e86">/rest/api/2/project</a>
 * 
 * @author lesstif
 *
 */
@Data
public class Project extends JsonPrettyString{
	
	private String self;
	
	private String id;
	private String key;
	
	private String name;
	
	private AvatarUrl avatarUrls; 
	private ProjectCategory projectCategory;
}
