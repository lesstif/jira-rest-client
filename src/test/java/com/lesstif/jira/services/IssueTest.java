package com.lesstif.jira.services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lesstif.jira.issue.Attachment;
import com.lesstif.jira.issue.Component;
import com.lesstif.jira.issue.Issue;
import com.lesstif.jira.issue.IssueFields;
import com.lesstif.jira.issue.IssueSearchResult;
import com.lesstif.jira.issue.IssueType;
import com.lesstif.jira.issue.Priority;
import com.lesstif.jira.util.HttpConnectionUtil;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class IssueTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeClass
    public static void beforeClass() {
        HttpConnectionUtil.disableSslVerification();
    }

    @Test
    public void getIssue() throws IOException, ConfigurationException {
        String issueKey = "TEST-92";

        IssueService issueService = new IssueService();
        Issue issue = issueService.getIssue(issueKey);

        logger.debug(issue.toPrettyJsonString());

        // attachment info
        List<Attachment> attachs = issue.getFields().getAttachment();
        for (Attachment a : attachs) {
            logger.debug("Attachment:" + a.toPrettyJsonString());
        }

        IssueFields fields = issue.getFields();

        // Project key
        logger.debug("Project Key:" + fields.getProject().getKey());

        //issue type
        logger.debug("IssueType:" + fields.getIssuetype().toPrettyJsonString());

        // issue description
        logger.debug("Issue Description:" + fields.getDescription());
    }

    @Test
    public void createIssue() throws IOException, ConfigurationException {

        Issue issue = new Issue();

        IssueFields fields = new IssueFields();

        fields.setProjectKey("TEST")
                .setSummary("something's wrong")
                .setIssueTypeName(IssueType.ISSUE_TYPE_TASK)
                .setDescription("Full description for issue")
                .setAssigneeName("lesstif");

        // Change Reporter need admin role
        fields.setReporterName("gitlab")
                .setPriorityName(Priority.PRIORITY_CRITICAL)
                .setLabels(new String[]{"bugfix", "blitz_test"})
                .setComponents(Arrays.asList(
                        new Component[]{new Component("Component-1"), new Component("Component-2")})
                )
                .addAttachment("readme.md")
                .addAttachment("bug-description.pdf")
                .addAttachment("screen_capture.png");

        issue.setFields(fields);

        logger.info(issue.toPrettyJsonString());

        IssueService issueService = new IssueService();

        Issue genIssue = issueService.createIssue(issue);

        //Print Generated issue
        logger.info(genIssue.toPrettyJsonString());
    }

    @Test
    public void uploadAttachments() throws IOException, ConfigurationException {
        Issue issue = new Issue();

        issue.setKey("TEST-92");

        issue.addAttachment(new File("attachment.png"));
        issue.addAttachment("test.pdf");

        IssueService issueService = new IssueService();
        issueService.postAttachment(issue);
    }

    //@Test
    public void getAllIssueType() throws IOException, ConfigurationException {

        IssueService issueService = new IssueService();
        List<IssueType> issueTypes = issueService.getAllIssueTypes();

        for (IssueType i : issueTypes) {
            logger.info(i.toPrettyJsonString());
        }
    }

    @Test
    public void getAllPriorities() throws IOException, ConfigurationException {

        IssueService issueService = new IssueService();
        List<Priority> priority = issueService.getAllPriorities();

        for (Priority p : priority) {
            logger.info(p.toPrettyJsonString());
        }
    }

    @Test
    public void getCustomeFields() throws IOException, ConfigurationException {
        IssueService issueService = new IssueService();
        Issue issue = issueService.getIssue("TEST-92");

        Map<String, Object> fields = issue.getFields().getCustomfield();

        for (String key : fields.keySet()) {
            logger.info("Field Name: " + key + ",value:" + fields.get(key));
        }
    }

    @Test
    public void getIssuesFromQuery() throws IOException, ConfigurationException {
        IssueService issueService = new IssueService();
        final String query = "status=open and assignee = currentUser()";
        
        final IssueSearchResult issues = issueService.getIssuesFromQuery(query);

        assertTrue("Should be at least 1 open issue", !issues.getIssues().isEmpty());

    }

}
