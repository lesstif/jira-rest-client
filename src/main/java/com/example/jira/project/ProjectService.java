package com.example.jira.project;
import java.util.List;

import lombok.Data;


/**
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/latest/#d2e86">/rest/api/2/project</a>
 * 
 * @author lesstif
 *
 */
@Data
public class ProjectService {
	
	public List<Project> listProjects() {
		return null;
	}
	
	private String self;
	
	private String id;
	private String key;
	
	private String name;
	
	//private List<String> avatarUrls;
	private AvatarUrl avatarUrls; 
	private ProjectCategory projectCategory;
}
