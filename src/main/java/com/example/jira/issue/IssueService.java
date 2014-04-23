package com.example.jira.issue;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

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
	private JIRAHTTPClient client = null;
	
	private Issue issue;
	
	public IssueService() throws ConfigurationException {
		client = new JIRAHTTPClient();
	}
	
	public Issue getIssue(String issueKey) throws JsonParseException, JsonMappingException, IOException {
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
	
	public void createIssue(Issue issue) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		//mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, true);
		String content = mapper.writeValueAsString(issue);
				
		client.setResourceName(Constants.JIRA_RESOURCE_ISSUE);
		
		ClientResponse response = client.post(content);
					
		content = response.getEntity(String.class);	
		
		
		
		
		//TypeReference<Issue> ref = new TypeReference<Issue>(){};
		
		
		return;
	}
}
