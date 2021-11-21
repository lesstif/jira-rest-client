package com.lesstif.jira;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JIRAHTTPClient {
	private ClientConfig clientConfig;
		
	private Client client;
	
	private WebTarget webTarget;
		
	private PropertiesConfiguration config = null;
	
	private final static String CONFIG_FILE = "jira-rest-client.properties";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String API_URL = "/rest/api/2/";
	
	private String url = null;
	private String user = null;
	private String password = null;
	private String pat = null;

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
		
		clientConfig = new ClientConfig();
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 5000);
		clientConfig.property(ClientProperties.FOLLOW_REDIRECTS, true);
		//clientConfig.property(ClientProperties.REQUEST_ENTITY_PROCESSING, true);

		clientConfig.register(MultiPartFeature.class);

		client = ClientBuilder.newClient(clientConfig);

		this.url = config.getString("jira.server.url");
		this.user = config.getString("jira.user.id");
		this.password = config.getString("jira.user.pwd");
		this.pat = config.getString("jira.user.pat");

		if (this.user != null && this.pat != null)
		{
//			HTTPBasicAuthFilter auth = new HTTPBasicAuthFilter(this.user, this.pat);
//			client.addFilter(auth);
		}
	}
	
	/**
	 * Setting JIRA API Resource Name
	 * 
	 * @param resourceName remote resource name
	 */
	public void setResourceName(String resourceName) {

		webTarget = client.target(this.url + API_URL).path(resourceName);
	}
	
	public Response get() {
		if (webTarget == null) {
			throw new IllegalStateException("webTarget is not Initializied. call setResourceName() method ");
		}

		Response response = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.pat)
				.get(Response.class);
		
		return checkStatus(response);
	}
		
	public Response post(String content) {
		if (webTarget == null) {
			throw new IllegalStateException("webResource is not Initializied. call setResourceName() method ");
		}

		Response response = webTarget.request(MediaType.APPLICATION_JSON)
									.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.pat)
									.post(Entity.text(content));
		
		return checkStatus(response);
	}

	public Response postMultiPart(MultiPart multiPart) {
		if (webTarget == null) {
			throw new IllegalStateException("webResource is not Initializied. call setResourceName() method ");
		}

		Response response = webTarget.request()
				.header("X-Atlassian-Token", "nocheck")
				//.type(MediaType.MULTIPART_FORM_DATA)
				.post(Entity.entity(multiPart, multiPart.getMediaType()));

		return checkStatus(response);
	}
	
	private Response checkStatus(Response response) {
		if (response.getStatus() != Response.Status.OK.getStatusCode()
				&& response.getStatus() != Response.Status.CREATED.getStatusCode()) {
			throw new IllegalStateException("Failed : HTTP error code : "	+ response.getStatus());
		}
		
		return response;
	}
}
