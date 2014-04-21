import static org.junit.Assert.fail;

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
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;


public class JIRARestAPIJerseyTest {

	public static final String JIRA_API_URL = "https://jira.ktnet.com/rest/api/2/";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Before
	public void setup() {

	}

	@After
	public void teardown() throws IOException {

	}

	@Test
	public void jerseyTest() {
		try {			
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			
			Client client = Client.create(clientConfig);
			
			client.addFilter(new HTTPBasicAuthFilter("rest-api", "apitest1"));
			WebResource webResource = client.resource(JIRA_API_URL + "project");
	
			ClientResponse response = webResource.accept("application/json").get(
					ClientResponse.class);
	
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
	
			String content = response.getEntity(String.class);	
			
			//System.out.println("Output from Server .... \n");
			//System.out.println(content);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			TypeReference ref = new TypeReference<List<Project>>(){};
			List<Project> prj = mapper.readValue(content, ref);
			
			int i = 0;
			for (Project p : prj) {
				System.out.println(i++ + "th " + prj + "\n");
			}
		}
		catch (Exception e) {
			 
			e.printStackTrace();
	 
		  }
	}
}
