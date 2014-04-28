package com.example.jira.project;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jira.issue.Attachment;
import com.example.jira.issue.Component;
import com.example.jira.issue.Issue;
import com.example.jira.issue.IssueFields;
import com.example.jira.issue.IssueService;
import com.example.jira.issue.IssueType;
import com.example.jira.issue.Priority;

public class IssueTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void getIssue() throws IOException, ConfigurationException {
		String issueKey = "TEST-833";

		IssueService issueService = new IssueService();
		Issue issue =  issueService.getIssue(issueKey);

		logger.info(issue.toString());
		// attachment info
		List<Attachment> attachs = issue.getFields().getAttachment();
		for ( Attachment a : attachs) 
			logger.info(a.toPrettyJsonString());
	}
	
	@Test
	public void createIssue() throws IOException, ConfigurationException {

		Issue issue = new Issue();
		
		IssueFields fields = new IssueFields();
		
		fields.setProjectKey("TEST");
		fields.setSummary("something's wrong");
		fields.setIssueTypeName(IssueType.ISSUE_TYPE_TASK);
		fields.setDescription("Full description for issue");
		fields.setAssigneeName("test");
		// Change Reporter need admin role
		fields.setReporterName("rest-api");
		fields.setPriorityName(Priority.PRIORITY_CRITICAL);
		fields.setLabels(new String[]{"bugfix","blitz_test"});
			
		fields.setComponents(Arrays.asList(new Component[]{new Component("Component-1"), new Component("Component-2")}));
		
		fields.addAttachment("c:\\Users\\lesstif\\test.pdf");
		fields.addAttachment("c:\\Users\\lesstif\\attachment.png");
		
		issue.setFields(fields);
		
		logger.info(issue.toString());
		
		IssueService issueService = new IssueService();

		Issue genIssue = issueService.createIssue(issue);		
		logger.info(genIssue.toPrettyJsonString());
	}
	
	@Test
	public void uploadAttachments() throws IOException, ConfigurationException {
		Issue issue = new Issue();
		
		issue.setKey("TEST-833");
				
		issue.addAttachment(new File("c:\\Users\\lesstif\\attachment.png"));
		issue.addAttachment("c:\\Users\\lesstif\\test.pdf");
		
		IssueService issueService = new IssueService();
		issueService.postAttachment(issue);
	}
	
	@Test
	public void getAllIssueType() throws IOException, ConfigurationException {

		IssueService issueService = new IssueService();
		List<IssueType> issueTypes =  issueService.getAllIssueTypes();

		logger.info(issueTypes.toString());
	}
	
	@Test
	public void getAllPriorities() throws IOException, ConfigurationException {

		IssueService issueService = new IssueService();
		List<Priority> priority =  issueService.getAllPriorities();

		logger.info(priority.toString());
	}
}
