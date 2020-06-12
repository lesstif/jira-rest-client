package com.lesstif.jira.services;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.lesstif.jira.Constants;
import com.lesstif.jira.JIRAHTTPClient;
import com.lesstif.jira.project.Project;
import com.sun.jersey.api.client.ClientResponse;

import lombok.Data;


/**
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/latest/#d2e86">/rest/api/2/project</a>
 * 
 * @author lesstif
 *
 */
@Data
public class ProjectService {
	private JIRAHTTPClient client = null;
	
	public ProjectService() throws ConfigurationException {
		client = new JIRAHTTPClient();
	}
	
	public List<Project> getProjectList() throws IOException {
		if (client == null)
			throw new IllegalStateException("HTTP Client not Initailized");
		
		client.setResourceName(Constants.JIRA_RESOURCE_PROJECT);
		ClientResponse response = client.get();
					
		String content = response.getEntity(String.class);	
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
		TypeReference<List<Project>> ref = new TypeReference<List<Project>>(){};
		List<Project> prj = mapper.readValue(content, ref);
		
		return prj;
	}
	
	// get Full project Information
	public Project getProjectDetail(String idOrKey) throws IOException {
		if (client == null)
			throw new IllegalStateException("HTTP Client not Initailized");
		
		client.setResourceName(Constants.JIRA_RESOURCE_PROJECT + "/" + idOrKey);
		ClientResponse response = client.get();
					
		String content = response.getEntity(String.class);	
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
		TypeReference<Project> ref = new TypeReference<Project>(){};
		Project prj = mapper.readValue(content, ref);
		
		return prj;
	}
}
