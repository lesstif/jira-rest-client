package com.example.jira.project;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jira.issue.Issue;
import com.example.jira.issue.IssueFields;
import com.example.jira.issue.IssueService;

public class IssueTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void getIssue() throws IOException, ConfigurationException {
		String issueKey = 
				//"TEST-824";
				"NCA-208";

		IssueService issueService = new IssueService();
		Issue issue =  issueService.getIssue(issueKey);

		logger.info(issue.toString());
	}
	
	@Test
	public void createIssue() throws IOException, ConfigurationException {

		Issue issue = new Issue();
		
		IssueFields fields = new IssueFields();
		
		fields.setProjectKey("TEST");
		fields.setSummary("something's wrong");
		fields.setIssueTypeId("10000");
		fields.setAssigneeName("test1");
		fields.setReporterName("lesstif");
		fields.setPriorityId("123");
		fields.setLabels(new String[]{"bugfix","blitz_test"});
		
		issue.setFields(fields);
		
		logger.info(issue.toString());
		
		IssueService issueService = new IssueService();

		issueService.createIssue(issue);		
	}
}
