package com.lesstif.jira.project;
import lombok.Data;

@Data
public class ProjectCategory {
	private String self;
	
	private String name;
	
	private String id;
	
	private String key;
	private String description;		
}