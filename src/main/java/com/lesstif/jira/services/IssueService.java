package com.lesstif.jira.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.Data;

import lombok.EqualsAndHashCode;
import org.apache.commons.configuration.ConfigurationException;
//import org.codehaus.jackson.JsonParseException;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.slf4j.Logger;

import com.lesstif.jira.Constants;
import com.lesstif.jira.JIRAHTTPClient;
import com.lesstif.jira.issue.Attachment;
import com.lesstif.jira.issue.Issue;
import com.lesstif.jira.issue.IssueSearchResult;
import com.lesstif.jira.issue.IssueType;
import com.lesstif.jira.issue.Priority;
import com.lesstif.jira.issue.WorklogElement;
import java.net.URLEncoder;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @see
 * <a href="https://docs.atlassian.com/software/jira/docs/api/REST/latest/#d2e86">/rest/api/2/project</a>
 *
 * @author lesstif
 *
 */
@Data
public class IssueService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    private JIRAHTTPClient client = null;

    private Issue issue;

    public IssueService() throws ConfigurationException {
        client = new JIRAHTTPClient();
    }

    public Issue getIssue(String issueKey) throws IOException {
        if (client == null) {
            throw new IllegalStateException("HTTP Client not Initailized");
        }

        client.setResourceName(Constants.JIRA_RESOURCE_ISSUE + "/" + issueKey);

        Response response = client.get();

        String content = response.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        TypeReference<Issue> ref = new TypeReference<Issue>() {
        };
        issue = mapper.readValue(content, ref);

        return issue;
    }

    public Issue createIssue(Issue issue) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //to ignore a field if its value is null
        // FIXME
        //  mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        String content = mapper.writeValueAsString(issue);

        logger.trace("Content=" + content);

        client.setResourceName(Constants.JIRA_RESOURCE_ISSUE);

        Response response = client.post(content);

        String responseContent = response.readEntity(String.class);

        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        TypeReference<Issue> ref = new TypeReference<Issue>() {
        };

        Issue resIssue = mapper.readValue(responseContent, ref);

        if (issue.hasAttachments()) {
            issue.setId(resIssue.getId());
            List<Attachment> attachment = postAttachment(issue);
            resIssue.getFields().setAttachment(attachment);
        }

        return resIssue;
    }

    /**
     * Returns a list of all issue types visible to the user
     *
     * @return List list of IssueType
     *
     * @throws IOException json decoding failed
     */
    public List<IssueType> getAllIssueTypes() throws IOException {

        client.setResourceName(Constants.JIRA_RESOURCE_ISSUETYPE);

        Response response = client.get();
        String content = response.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        TypeReference<List<IssueType>> ref = new TypeReference<List<IssueType>>() {
        };
        List<IssueType> issueTypes = mapper.readValue(content, ref);

        return issueTypes;
    }

    public List<Priority> getAllPriorities() throws IOException {

        client.setResourceName(Constants.JIRA_RESOURCE_PRIORITY);

        Response response = client.get();
        String content = response.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        TypeReference<List<Priority>> ref = new TypeReference<List<Priority>>() {
        };
        List<Priority> priorities = mapper.readValue(content, ref);

        return priorities;
    }

    /**
     * Add one or more attachments to an issue.
     *
     * @param issue Issue object
     * @return List
     * @throws IOException general IO exception
     */
    public List<Attachment> postAttachment(Issue issue) throws IOException {
        List<File> files = issue.getFields().getFileList();

        if (files == null || files.size() == 0) {
            throw new IllegalStateException("Oops! Attachment Not Found.");
        }

        if ((issue.getId() == null || issue.getId().isEmpty())
                && (issue.getKey() == null || issue.getKey().isEmpty())) {
            throw new IllegalStateException("Oops! Issue id or Key not set.");
        }

        String idOrKey = issue.getId() == null ? issue.getKey() : issue.getId();

        FormDataMultiPart form = new FormDataMultiPart();
        for (int i = 0; i < files.size(); i++) {
            // The name of the multipart/form-data parameter that contains attachments must be "file"
            FileDataBodyPart fdp = new FileDataBodyPart("file", files.get(i));

            form.bodyPart(fdp);
        }

        client.setResourceName(Constants.JIRA_RESOURCE_ISSUE + "/" + idOrKey + "/attachments");

        Response response = client.postMultiPart(form);
        String content = response.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        TypeReference<List<Attachment>> ref = new TypeReference<List<Attachment>>() {
        };
        List<Attachment> res = mapper.readValue(content, ref);

        return res;
    }

    /**
     * Add one or more worklog to an issue.
     *
     * @param worklog Issue object
     * @param issue
     * @return List
     * @throws JsonParseException json parsing failed
     * @throws JsonMappingException json mapping failed
     * @throws IOException general IO exception
     */
    public WorklogElement postWorklog(final WorklogElement worklog, final Issue issue) throws JsonParseException, JsonMappingException, IOException {
        
        String issueId = issue.getId();
        
        if (StringUtils.isBlank(issueId)) {
            final String key = issue.getKey();
            
            final Issue issueByKey = getIssue(key);
            
            issueId = issueByKey.getId();
            
        }
        
        ObjectMapper mapper = new ObjectMapper();
       
        //to ignore a field if its value is null
        //FIXME mapper.getSerializationConfig().setSerializationInclusion(JsonInclude);
        String content = mapper.writeValueAsString(worklog);
        final WorklogElement result = postWorklog(content, issueId);

        return result;
    }

    public WorklogElement postWorklog(String content, final String issueId) throws IOException {
        ObjectMapper mapper;
        //content = "{\"comment\":\"I did some work here.\",\"started\":\"2016-03-23T04:22:37.471+0000\",\"timeSpentSeconds\":12000}";
        logger.debug("Content=" + content);
        final String resource = Constants.JIRA_RESOURCE_ISSUE + "/" + issueId + "/worklog";
        client.setResourceName(resource);
        Response response = client.post(content);
        content = response.readEntity(String.class);

        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        TypeReference<WorklogElement> ref = new TypeReference<WorklogElement>() {
        };
        WorklogElement resIssue = mapper.readValue(content, ref);
        return resIssue;
    }

    public IssueSearchResult getIssuesFromQuery(final String query) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //FIXME
        // mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        String content = URLEncoder.encode(query, "UTF-8");

        final String resource = "search?jql=" + content;
        
        logger.debug("resource=" + resource);

        client.setResourceName(resource);

        Response response = client.get();

        content =  response.readEntity(String.class);

        TypeReference<IssueSearchResult> ref = new TypeReference<IssueSearchResult>() {
        };

        IssueSearchResult issues = mapper.readValue(content, ref);

        return issues;
    }
}