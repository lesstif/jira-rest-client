package com.example.jira;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.example.jira.project.Project;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;

public class JIRAHTTPClient {
	private ClientConfig clientConfig;
		
	private Client client;
	
	private WebResource webResource;
		
	private PropertiesConfiguration config = null;
	
	public JIRAHTTPClient() throws ConfigurationException {
		config = new PropertiesConfiguration("jira-rest-client.properties");
		clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.FALSE);
		
		client = Client.create(clientConfig);
		
		HTTPBasicAuthFilter auth = null;
		
		if (config.getString("jira.user.id") != null && config.getString("jira.user.pwd") != null)
		{
			auth = new HTTPBasicAuthFilter(config.getString("jira.user.id"), config.getString("jira.user.pwd"));
			client.addFilter(auth);
		}
	}
	
	/**
	 * 
	 * Setting JIRA API Resource Name
	 * 
	 * Structure of the JIRA REST API URIS
	 * 
	 * http://host:port/context/rest/api-name/api-version/resource-name
	 * 
	 * @see https://docs.atlassian.com/software/jira/docs/api/REST/latest/ 
	 * 	
	 * @param url
	 */
	public void setResourceName(String resourceName) {
		webResource = client.resource(config.getString("jira.server.url") + resourceName);
	}
	
	public ClientResponse get() {
		if (webResource == null) {
			throw new IllegalStateException("webResource is not Initializied. call setResourceName() method ");
		}
		
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "	+ response.getStatus());
		}
		
		return response;
	}
		
	public ClientResponse post(String content) {
		if (webResource == null) {
			throw new IllegalStateException("webResource is not Initializied. call setResourceName() method ");
		}
		
		ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "	+ response.getStatus());
		}
		
		return response;
	}
}
