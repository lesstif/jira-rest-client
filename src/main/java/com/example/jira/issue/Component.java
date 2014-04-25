package com.example.jira.issue;

import java.util.Map;

import lombok.Data;

@Data
public class Component {
	public Component(String name) {
		this.name = name;
	}
	
	public Component(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	private String self;
	private String id;
	private String name;
	private String description;
	
	private Map<String, String> lead;
	private String displayName;
	private Boolean active;
}
