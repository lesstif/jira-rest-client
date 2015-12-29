package com.lesstif.jira;

import java.io.File;

import javax.ws.rs.core.MediaType;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.multipart.MultiPart;

public class JIRAHTTPClient {
	private ClientConfig clientConfig;
		
	private Client client;
	
	private WebResource webResource;
		
	private PropertiesConfiguration config = null;
	
	private final static String CONFIG_FILE = "jira-rest-client.properties";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String API_URL = "/rest/api/2/";
	
	public JIRAHTTPClient() throws ConfigurationException {
		org.slf4j.bridge.SLF4JBridgeHandler.removeHandlersForRootLogger();		
		org.slf4j.bridge.SLF4JBridgeHandler.install();
		
		// search properties, in this order.
		// #1. specified system property (run jvm with -Djira.client.property=absolute_config_path)
		// #2. current directory
		// #3. in the library jar(jira-rest-api.jar)		
		
		File f = null;
		if (System.getProperty("jira.client.property") != null)
			f = new File(System.getProperty("jira.client.property"));
		
		if (f != null && f.exists()) {
			logger.info("Using Configuration " + f.getAbsolutePath());
			config = new PropertiesConfiguration(f);
		} else {
			f = new File(new File("."), CONFIG_FILE);
			if (f.exists()) {
				logger.info("Using Configuration " + f.getAbsolutePath());
				config = new PropertiesConfiguration(f);
			}
			else {
				logger.info("Using default Configuration");
				config = new PropertiesConfiguration(CONFIG_FILE);
			}
		}		
		
		clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.FALSE);
		
		client = Client.create(clientConfig);
		
		client.addFilter(new LoggingFilter());
		
		if (config.getString("jira.user.id") != null && config.getString("jira.user.pwd") != null)
		{
			HTTPBasicAuthFilter auth = new HTTPBasicAuthFilter(config.getString("jira.user.id"), config.getString("jira.user.pwd"));
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
		webResource = client.resource(config.getString("jira.server.url") + API_URL + resourceName);
	}
	
	public ClientResponse get() {
		if (webResource == null) {
			throw new IllegalStateException("webResource is not Initializied. call setResourceName() method ");
		}
		
		ClientResponse response = webResource.accept("application/json")
				.type(MediaType.APPLICATION_JSON)				
				.get(ClientResponse.class);
		
		return checkStatus(response);
	}
		
	public ClientResponse post(String content) {
		if (webResource == null) {
			throw new IllegalStateException("webResource is not Initializied. call setResourceName() method ");
		}
		
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, content);
		
		return checkStatus(response);
	}

	public ClientResponse postMultiPart(MultiPart multiPart) {
		if (webResource == null) {
			throw new IllegalStateException("webResource is not Initializied. call setResourceName() method ");
		}
 		
		ClientResponse response = webResource
				.header("X-Atlassian-Token", "nocheck")
				.type(MediaType.MULTIPART_FORM_DATA)
				.post(ClientResponse.class, multiPart);
		
		return checkStatus(response);
	}
	
	private ClientResponse checkStatus(ClientResponse response) {
		if (response.getStatus() != Status.OK.getStatusCode() && response.getStatus() != Status.CREATED.getStatusCode()) {
			throw new ClientHandlerException("Failed : HTTP error code : "	+ response.getStatus());
		}
		
		return response;
	}
}
