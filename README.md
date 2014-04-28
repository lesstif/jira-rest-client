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
    * Get All Issue type
    * Create Issue
    * Attachment upload/download

## TODO ##
* 

## Maven Dependency ##
```xml
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>jira-rest-client</artifactId>
      <version>0.0.2</version>
    </dependency>
```

## Example ##

### print  All Projects ###
```java
	
	@Test
	public void listProject() throws JsonParseException, JsonMappingException, IOException, ConfigurationException {
		ProjectService prjService = new ProjectService();
		
		List<Project> prj = prjService.getProjectList();
		
		int i = 0;
		for (Project p : prj) {
			System.out.println(i++ + "th " + p.toPrettyJsonString() );
		}
	}	

```

### Get Issue by Issue-Key ###
```java

    @Test
	public void getIssue() throws IOException, ConfigurationException {
		String issueKey = "TEST-833";

		IssueService issueService = new IssueService();
		Issue issue =  issueService.getIssue(issueKey);

		logger.info(issue.toPrettyJsonString());

		// attachment info
		List<Attachment> attachs = issue.getFields().getAttachment();
		for ( Attachment a : attachs) 
			logger.info(a.toPrettyJsonString());
	}

```

### Create Issue ###
```java

public void createIssue() throws JsonParseException, JsonMappingException, IOException, ConfigurationException {

	Issue issue = new Issue();
		
		IssueFields fields = new IssueFields();
		
		fields.setProjectKey("TEST");
		fields.setSummary("something's wrong");
		fields.setIssueTypeName(IssueType.ISSUE_TYPE_TASK);
		fields.setDescription("Full description for issue");
		fields.setAssigneeName("test");
		
		// Change Reporter need admin role
		fields.setReporterName("rest-api");
		fields.setPriorityName(Priority.PRIORITY_CRITICAL);
		fields.setLabels(new String[]{"bugfix","blitz_test"});
			
		fields.setComponents(Arrays.asList(new Component[]{new Component("Component-1"), new Component("Component-2")}));
		
		fields.addAttachment("c:\\Users\\lesstif\\test.pdf");
		fields.addAttachment("c:\\Users\\lesstif\\attachment.png");
		
		issue.setFields(fields);
		
		logger.info(issue.toString());
		
		IssueService issueService = new IssueService();
		
		Issue genIssue = issueService.createIssue(issue);		
		
		//Print Generated issue
		logger.info(genIssue.toPrettyJsonString());
	}

```

### Upload attachment After Issue Created ###
```java

public void uploadAttachments() throws IOException, ConfigurationException {

		Issue issue = new Issue();
		
		issue.setKey("TEST-834");
				
		issue.addAttachment(new File("c:\\Users\\lesstif\\attachment.png"));
		issue.addAttachment("c:\\Users\\lesstif\\test.pdf");
		
		IssueService issueService = new IssueService();
		issueService.postAttachment(issue);
	}
	
```

### print all Issue Priority Type ###
```java

public void getAllPriorities() throws IOException, ConfigurationException {


		IssueService issueService = new IssueService();
		List<Priority> priority =  issueService.getAllPriorities();

		logger.info(priority.toString());
	}
```

### print all Custom Field in the Issue ###
```java

    public void getCustomeFields() throws IOException, ConfigurationException {
		IssueService issueService = new IssueService();
		Issue issue =  issueService.getIssue("TEST-834");

		logger.info(issue.getFields().getCustomfield().toString());		
	}
```