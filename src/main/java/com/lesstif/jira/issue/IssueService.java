package com.lesstif.jira.issue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.Data;

import org.apache.commons.configuration.ConfigurationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;

import com.lesstif.jira.Constants;
import com.lesstif.jira.JIRAHTTPClient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;


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
                
        if(issue.hasAttachments()) {
     		issue.setId(resIssue.getId());
     		List<Attachment> attachment = postAttachment(issue);
     		resIssue.getFields().setAttachment(attachment);
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

	/**
	 * Add one or more attachments to an issue.
	 * @param issue
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public List<Attachment> postAttachment(Issue issue) throws JsonParseException, JsonMappingException, IOException {
        List<File> files = issue.getFields().getFileList();
        
        if (files == null || files.size() == 0 )
        	throw new IllegalStateException("Oops! Attachment Not Found.");
        	
        if ( (issue.getId() == null || issue.getId().isEmpty()) &&
        		(issue.getKey() == null || issue.getKey().isEmpty()) )
        	throw new IllegalStateException("Oops! Issue id or Key not set.");

        String idOrKey = issue.getId() == null ? issue.getKey() : issue.getId();
        
        FormDataMultiPart form = new FormDataMultiPart();
 		for (int i = 0; i < files.size(); i++) {
 			// The name of the multipart/form-data parameter that contains attachments must be "file"
    	    FileDataBodyPart fdp = new FileDataBodyPart("file", files.get(i));
    	    
    	    form.bodyPart(fdp);
 		}
		 
 		client.setResourceName(Constants.JIRA_RESOURCE_ISSUE + "/" + idOrKey + "/attachments");
		
 		ClientResponse response = client.postMultiPart(form);     	
 		String content = response.getEntity(String.class);
 		
 		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);		
        
        TypeReference<List<Attachment>> ref = new TypeReference<List<Attachment>>(){};
		List<Attachment> res = mapper.readValue(content, ref);
		
		return res;
	}
	
}
