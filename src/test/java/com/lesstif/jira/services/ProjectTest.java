package com.lesstif.jira.services;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.lesstif.jira.issue.IssueType;
import org.apache.commons.configuration.ConfigurationException;
import org.hamcrest.Matchers;
import org.hamcrest.text.IsEmptyString;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lesstif.jira.project.Project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProjectTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void listProject() throws IOException, ConfigurationException {
		ProjectService prjService = new ProjectService();
		
		List<Project> projects = prjService.getProjectList();
		
		int i = 0;
		for (Project prj : projects) {
			logger.info(i++ + "th " + prj.toPrettyJsonString() );

			assertThat(prj.getId(), notNullValue());
			assertThat(prj.getName(), notNullValue());
			//assertThat(prj.getDescription(), not(emptyOrNullString()));

			List<IssueType> issueTypes = prj.getIssueTypes();
			if (issueTypes != null) {
				assertThat(issueTypes.size(), greaterThan(1));
			}
		}
	}	
	
	@Test
	public void getProject() throws IOException, ConfigurationException {
		String projectKey = "TEST";

		ProjectService prjService = new ProjectService();
		
		Project prj = prjService.getProjectDetail(projectKey);

		assertThat(prj.getId(), notNullValue());
		assertThat(prj.getName(), notNullValue());
		//assertThat(prj.getDescription(), not(emptyOrNullString()));
		assertThat(prj.getKey(), is(projectKey));
		assertThat(prj.getIssueTypes().size(), greaterThan(3));

		logger.info("P=" + prj.toPrettyJsonString());
	}	
}
