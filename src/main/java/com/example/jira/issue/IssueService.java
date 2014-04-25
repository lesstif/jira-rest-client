package com.example.jira.issue;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;

import com.example.jira.Constants;
import com.example.jira.JIRAHTTPClient;
import com.sun.jersey.api.client.ClientResponse;

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
	
	public void createIssue(Issue issue) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		//to ignore a field if its value is null
		mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		String content = mapper.writeValueAsString(issue);
				
		logger.debug("Content=" + content);
		
		client.setResourceName(Constants.JIRA_RESOURCE_ISSUE);
		
		ClientResponse response = client.post(content);
					
		content = response.getEntity(String.class);	
				
		return;
	}
}
