package com.example.jira.issue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;

import com.example.jira.Constants;
import com.example.jira.JIRAHTTPClient;
import com.example.jira.project.Project;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;

import lombok.Data;


/**
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/latest/#d2e86">/rest/api/2/project</a>
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
		if (client == null)
			throw new IllegalStateException("HTTP Client not Initailized");
		
		client.setResourceName(Constants.JIRA_RESOURCE_ISSUE + "/" + issueKey);
		
		ClientResponse response = client.get();
					
		String content = response.getEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);		
		TypeReference<Issue> ref = new TypeReference<Issue>(){};
		issue = mapper.readValue(content, ref);
				
		return issue;
	}	
	
	public Issue createIssue(Issue issue) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		//to ignore a field if its value is null
		mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		String content = mapper.writeValueAsString(issue);
				
		logger.debug("Content=" + content);
		
		client.setResourceName(Constants.JIRA_RESOURCE_ISSUE);
		
		ClientResponse response = client.post(content);
					
		content = response.getEntity(String.class);	
				
		mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        TypeReference<Issue> ref = new TypeReference<Issue>(){};
        
        Issue resIssue = mapper.readValue(content, ref); 

        // Add one or more attachments to an issue.
        List<Attachment> attachs = issue.getFields().getAttachment();
     	if (attachs != null && attachs.size() > 0 ) {
     		//MultiPart multiPart = new MultiPart();
     		MultiPart form = new FormDataMultiPart();
     		for (int i = 0; i < attachs.size(); i++) {
	    		
	    		BodyPart bodyPart = new BodyPart();	
	    	    bodyPart.setEntity(attachs.get(i).getContentData());
	    	    	    	    
	    	    form.bodyPart(bodyPart);
     		}
     		client.setResourceName(Constants.JIRA_RESOURCE_ISSUE + "/" + resIssue.getId() + "/attachments");
    		
    		response = client.post(content);
     	}
     		
     	return resIssue;
	}
	
	/**
	 * Returns a list of all issue types visible to the user
	 * 
	 * @throws IOException
	 */
	public List<IssueType> getAllIssueTypes() throws IOException {
						
		client.setResourceName(Constants.JIRA_RESOURCE_ISSUETYPE);
		
		ClientResponse response = client.get();
		String content = response.getEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);		
        
        TypeReference<List<IssueType>> ref = new TypeReference<List<IssueType>>(){};
		List<IssueType> issueTypes = mapper.readValue(content, ref);
						
		return issueTypes;
	}
	
	public List<Priority> getAllPriorities() throws IOException {
		
		client.setResourceName(Constants.JIRA_RESOURCE_PRIORITY);
		
		ClientResponse response = client.get();
		String content = response.getEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);		
        
        TypeReference<List<Priority>> ref = new TypeReference<List<Priority>>(){};
		List<Priority> priorities = mapper.readValue(content, ref);
						
		return priorities;
	}
	
}
