# jira-rest-client #

Atlassian's JIRA REST API Implementation for Java.

jira-rest-client depends on [jersey-client](https://jersey.java.net/documentation/latest/client.html), [Jackson Json Processor](https://github.com/FasterXML/jackson), [Project Lombok](http://projectlombok.org/), [Joda-Time](http://www.joda.org/joda-time/).

JIRA REST API documentation can be found [here](https://docs.atlassian.com/jira/REST/latest/)

## Implemented APIs ##

*  Authentication
	*  HTTP Basic Authentication
*  Projects
    * List all Projects
* Issues
    * Get Issue by Issue Key

## TODO ##
* Processing custom field (customfield_xxxxxx)

## Maven Dependency ##
```xml
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>jira-rest-client</artifactId>
      <version>0.0.1</version>
    </dependency>
```

## Example ##

### print  All Projects ###
```java

package com.example.jira.project;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void listProject() throws JsonParseException, JsonMappingException, IOException, ConfigurationException {
		ProjectService prjService = new ProjectService();
		
		List<Project> prj = prjService.getProjectList();
		
		int i = 0;
		for (Project p : prj) {
			logger.info(i++ + "th " + p );
		}
	}	
}

```

### Get Issue by Issue-Key ###
```java

@Test
	public void getIssue() throws JsonParseException, JsonMappingException, IOException, ConfigurationException {
		String issueKey = 
				//"TEST-824";
				"NCA-208";

		IssueService issueService = new IssueService();
		Issue issue =  issueService.getIssue(issueKey);

		logger.info(issue.toString());
	}

```

### Create Issue ###
```java

public void createIssue() throws JsonParseException, JsonMappingException, IOException, ConfigurationException {

		Issue issue = new Issue();
		
		IssueFields fields = new IssueFields();
		
		fields.setProjectId("10000");
		fields.setSummary("something's wrong");
		fields.setIssueTypeId("10000");
		fields.setAssigneeName("test1");
		fields.setReporterName("lesstif");
		fields.setPriorityId("123");
		fields.setLabels(new String[]{"bugfix","blitz_test"});
		
		logger.info(issue.toString());
		
		IssueService issueService = new IssueService();

		issueService.createIssue(issue);		
	}

```