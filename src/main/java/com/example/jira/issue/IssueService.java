package com.example.jira.issue;
import java.io.IOException;
import java.util.List;

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
	
	private JIRAIssue issue;
	
	public IssueService() {
		client = new JIRAHTTPClient();
	}
	
	public JIRAIssue getIssue(String issueKey) throws JsonParseException, JsonMappingException, IOException {
		if (client == null)
			throw new IllegalStateException("HTTP Client not Initailized");
		
		client.setResourceName(Constants.JIRA_RESOURCE_ISSUE + "/" + issueKey);
		
		ClientResponse response = client.getResponse();
					
		String content = response.getEntity(String.class);	
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
		TypeReference<JIRAIssue> ref = new TypeReference<JIRAIssue>(){};
		issue = mapper.readValue(content, ref);
		
		return issue;
	}	
}
