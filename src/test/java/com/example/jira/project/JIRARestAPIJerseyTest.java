package com.example.jira.project;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jira.JIRAHTTPClient;
import com.example.jira.issue.IssueService;
import com.example.jira.issue.JIRAIssue;

public class JIRARestAPIJerseyTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void listProject() throws JsonParseException, JsonMappingException, IOException {
		ProjectService prjService = new ProjectService();
		
		List<Project> prj = prjService.getProjectList();
		
		int i = 0;
		for (Project p : prj) {
			logger.info(i++ + "th " + p );
		}
	}
	
	@Test
	public void getIssue() throws JsonParseException, JsonMappingException, IOException {
		String issueKey = "TEST-824";
		
		IssueService issueService = new IssueService();
		JIRAIssue issue =  issueService.getIssue(issueKey);

		logger.info(issue.toString());
	}
}
