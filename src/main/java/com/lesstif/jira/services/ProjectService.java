package com.lesstif.jira.services;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.commons.configuration.ConfigurationException;
import com.lesstif.jira.Constants;
import com.lesstif.jira.JIRAHTTPClient;
import com.lesstif.jira.project.Project;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;


/**
 * @see <a href="https://docs.atlassian.com/software/jira/docs/api/REST/latest/#d2e86">/rest/api/2/project</a>
 * 
 * @author lesstif
 *
 */
@Data
public class ProjectService {
	private JIRAHTTPClient client = null;

	private Logger logger = LoggerFactory.getLogger(ProjectService.class);

	public ProjectService() throws ConfigurationException {
		client = new JIRAHTTPClient();
	}
	
	public List<Project> getProjectList() throws IOException {
		if (client == null)
			throw new IllegalStateException("HTTP Client not Initailized");
		
		client.setResourceName(Constants.JIRA_RESOURCE_PROJECT);
		Response response = client.get();
					
		String content = response.readEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

		TypeReference<List<Project>> ref = new TypeReference<List<Project>>(){};
		List<Project> prj = mapper.readValue(content, ref);
		
		return prj;
	}
	
	// get Full project Information
	public Project getProjectDetail(String idOrKey) throws IOException {
		if (client == null)
			throw new IllegalStateException("HTTP Client not Initailized");
		
		client.setResourceName(Constants.JIRA_RESOURCE_PROJECT + "/" + idOrKey);
		Response response = client.get();
					
		String content = response.readEntity(String.class);

		logger.debug("ProjectDetail=>" + content);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
		TypeReference<Project> ref = new TypeReference<Project>(){};
		Project prj = mapper.readValue(content, ref);
		
		return prj;
	}
}
