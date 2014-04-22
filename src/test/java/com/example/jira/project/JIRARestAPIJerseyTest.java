package com.example.jira.project;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jira.issue.IssueService;
import com.example.jira.issue.Issue;

public class JIRARestAPIJerseyTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void listProject() throws JsonParseException, JsonMappingException, IOException, ConfigurationException {
		ProjectService prjService = new ProjectService();
		
		List<Project> prj = prjService.getProjectList();
		
		int i = 0;
		for (Project p : prj) {
			logger.info(i++ + "th " + p );
		}
	}
	
	@Test
	public void getIssue() throws JsonParseException, JsonMappingException, IOException, ConfigurationException {
		String issueKey = "TEST-824";
		
		IssueService issueService = new IssueService();
		Issue issue =  issueService.getIssue(issueKey);

		logger.info(issue.toString());
	}
}
