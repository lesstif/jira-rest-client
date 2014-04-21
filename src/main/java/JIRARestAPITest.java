import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JIRARestAPITest {

	public static final String JIRA_API_URL = "https://jira.ktnet.com/rest/api/2/";

	private Logger logger = LoggerFactory.getLogger(getClass());

	private CloseableHttpClient hc = null;

	@Before
	public void setup() {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				new AuthScope("jira.ktnet.com", 443, null), 
				new UsernamePasswordCredentials("rest-api", "apitest1")
		);
		
		hc = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
	}

	@After
	public void teardown() throws IOException {
		hc.close();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void CreateIssue() throws ClientProtocolException, IOException {

		try {
			HttpPost httppost = new HttpPost(JIRA_API_URL + "issue/");

			HttpResponse response = hc.execute(httppost);
			HttpEntity entity = response.getEntity();

			logger.info("Login form get: " + response.getStatusLine());
			if (entity != null) {
				entity.consumeContent();
			}
			// CloseableHttpResponse response = hc.execute(host);

			logger.info("Executing request " + hc.toString());
		} finally {

		}
	}

	@Test
	public void listProjects() throws ClientProtocolException, IOException {

		InputStream is = null;

		try {			
			HttpGet httpget = new HttpGet(JIRA_API_URL + "project");

			httpget.addHeader("Content-Type", "application/json");			
			//httpget.addHeader("Authorization", "Basic cmVzdC1hcGk6YXBpdGVzdDE=");
				
			CloseableHttpResponse response = hc.execute(httpget);
			HttpEntity entity = response.getEntity();

			logger.info("Login form get: " + response.getStatusLine());

			if (entity != null) {
				String responseString = EntityUtils.toString(entity, "UTF-8");

				System.err.println("EntityUtils.toString" + responseString);

				System.out.println(EntityUtils.getContentMimeType(entity));
				System.out.println(EntityUtils.getContentCharSet(entity));
			}

			logger.info("Project Lists " + hc.toString());
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	@Test
	public void example() throws Exception {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(null, -1),
                new UsernamePasswordCredentials("rest-api", "password"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpGet httpget = new 
            		//HttpGet(JIRA_API_URL);
            		HttpGet("https://jira.ktnet.com/rest/api/2/project");

            //System.out.println("Executing request " + httpget.getRequestLine() + "\n\n\n");
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
               // System.out.println("----------------------------------------");
                //System.out.println(response.getStatusLine());
                //EntityUtils.consume(response.getEntity());
            	String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

				System.err.println("EntityUtils.toString" + responseString);

            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
	
	@Test
	public void jerseyTest() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(MyClientResponseFilter.class);
		clientConfig.register(new AnotherClientFilter());
		 
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(ThirdClientFilter.class);
		 
		WebTarget webTarget = client.target("http://example.com/rest");
		webTarget.register(FilterForExampleCom.class);
		WebTarget resourceWebTarget = webTarget.path("resource");
		WebTarget helloworldWebTarget = resourceWebTarget.path("helloworld");
		WebTarget helloworldWebTargetWithQueryParam =
		        helloworldWebTarget.queryParam("greeting", "Hi World!");
		 
		Invocation.Builder invocationBuilder =
		        helloworldWebTargetWithQueryParam.request(MediaType.TEXT_PLAIN_TYPE);
		invocationBuilder.header("some-header", "true");
		 
		Response response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}
}
