package com.example.jira.project;
import static org.junit.Assert.fail;

import java.awt.Container;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jira.Constants;
import com.example.jira.JIRAHTTPClient;
import com.example.jira.project.Project;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;

public class JIRARestAPIJerseyTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private JIRAHTTPClient client ;
	@Before
	public void setup() {
		client = new JIRAHTTPClient();
	}

	@After
	public void teardown() throws IOException {
		client = null;
	}

	@Test
	public void listProject() throws JsonParseException, JsonMappingException, IOException {

		List<Project> prj = client.getProjectList();
		
		int i = 0;
		for (Project p : prj) {
			logger.info(i++ + "th " + p );
		}
	}
}
