# Jira-rest-client

Atlassian's JIRA REST API Implementation for Java.

jira-rest-client depends on [jersey-client](https://jersey.java.net/documentation/latest/client.html), [Jackson Json Processor](https://github.com/FasterXML/jackson), [Project Lombok](http://projectlombok.org/), [Joda-Time](http://www.joda.org/joda-time/).

JIRA REST API documentation can be found [here](https://docs.atlassian.com/jira/REST/latest/)

# Installation

1. check latest released artifact version from Sonatype OSPRH(Open Source Project Repository Hosting) repository.	(https://oss.sonatype.org/content/groups/public/com/lesstif/jira-rest-api/)  

2. add this dependency snippet into pom.xml. 
	```xml
	    <dependency>
	      <groupId>com.lesstif</groupId>
	      <artifactId>jira-rest-client</artifactId>
	      <version>EDIT_THIS</version>
	    </dependency>
	```

3. create *jira-rest-client.properties* file into directory in the CLASS PATH variable and set your jira host and auth infos.    
	```
	JIRA_HOST="https://your-jira.host.com"
	JIRA_USER="jira-username"
	JIRA_PASS="jira-password"
	```
	
# Usage
	
## Table of Contents

- [Get Project Info](#get-project-info)
- [Get All Projects](#get-all-projects)
- [Get Issue Info](#get-issue-info)
- [Create Issue](#create-issue)
- [Add Attachment](#add-attachment)
- [Update issue](#update-issue)
- [Get all Priority Type](#get-all-priority-type)
- [Get all Issue Type](#get-all-issue-type)
- [Get all Custom Field in the Issue](#get-all-custom-field-in-the-issue)


## get project info
```java	
@Test
	public void getProject() throws IOException, ConfigurationException {
		ProjectService prjService = new ProjectService();
		
		Project prj = prjService.getProjectDetail("TEST");
		
		logger.info("P=" + prj.toPrettyJsonString());
	}
```
		
## get all projects
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

## Get Issue Info
```java
@Test
public void getIssue() throws IOException, ConfigurationException {
	String issueKey = "TEST-833";

	IssueService issueService = new IssueService();
	Issue issue =  issueService.getIssue(issueKey);

	logger.info(issue.toPrettyJsonString());

	// attachment info
	List<Attachment> attachs = issue.getFields().getAttachment();
	for ( Attachment a : attachs)  {
		logger.info(a.toPrettyJsonString());
	}
	IssueFields fields = issue.getFields();

	// Project key
	logger.debug("Project Key:" + fields.getProject().getKey());

	//issue type
	logger.debug("IssueType:" + fields.getIssuetype().toPrettyJsonString());
	
	// issue description
	logger.debug("Issue Description:" + fields.getDescription());
}
```

## Create Issue
```java
public void createIssue() throws JsonParseException, JsonMappingException, IOException, ConfigurationException {
        Issue issue = new Issue();
        
		IssueFields fields = new IssueFields();
		fields.setProjectKey("TEST")
			  .setSummary("something's wrong")
			  .setIssueTypeName(IssueType.ISSUE_TYPE_TASK)
			  .setDescription("Full description for issue")
			  .setAssigneeName("lesstif")
			  .setReporterName("gitlab") // Change Reporter need admin role
			  .setPriorityName(Priority.PRIORITY_CRITICAL)
			  .setLabels(new String[]{"bugfix","blitz_test"})			
			  .setComponents(Arrays.asList(
					new Component[]{new Component("Component-1"), new Component("Component-2")})
					)
			 .addAttachment("readme.md")
			 .addAttachment("bug-description.pdf")
			 .addAttachment("screen_capture.png");
		
		issue.setFields(fields);
		
		logger.debug(issue.toPrettyJsonString());
		
		IssueService issueService = new IssueService();
		
		Issue genIssue = issueService.createIssue(issue);		
		
		//Print Generated issue
		logger.info(genIssue.toPrettyJsonString());
	}

```

## Add Attachment
```java
public void uploadAttachments() throws IOException, ConfigurationException 
{
    Issue issue = new Issue();
	issue.setKey("TEST-834");
	
	issue.addAttachment(new File("c:\\Users\\lesstif\\attachment.png"));
	issue.addAttachment("c:\\Users\\lesstif\\test.pdf");
	
	IssueService issueService = new IssueService();
	issueService.postAttachment(issue);
}
	
```

## Get all Issue Type
```java
public void getAllPriorities() throws IOException, ConfigurationException 
{
	IssueService issueService = new IssueService();
	List<IssueType> issueTypes =  issueService.getAllIssueTypes();

	for(IssueType i : issueTypes) {
	    logger.info(i.toPrettyJsonString());
	}
}
```

## Get all Priority Type
```java
public void getAllPriorities() throws IOException, ConfigurationException 
{
	IssueService issueService = new IssueService();
	List<Priority> priority =  issueService.getAllPriorities();

	for(Priority p : priority) {
		logger.info(p.toPrettyJsonString());
	}
}
```

## get all Custom Field in the Issue
```java
public void getCustomeFields() throws IOException, ConfigurationException 
{
	IssueService issueService = new IssueService();
	Issue issue =  issueService.getIssue("TEST-92");
	
	Map<String, Object> fields = issue.getFields().getCustomfield();
	
	for( String key : fields.keySet() ){
		logger.info("Field Name: " + key + ",value:" + fields.get(key));
	}
}
```

## Update issue
not implemented yet

## transition issue
not implemented yet

## advanced search using JQL
not implemented yet

## issue time tracking
not implemented yet
