package com.lesstif.jira.services;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lesstif.jira.project.Project;

public class ProjectTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void listProject() throws IOException, ConfigurationException {
		ProjectService prjService = new ProjectService();
		
		List<Project> prj = prjService.getProjectList();
		
		int i = 0;
		for (Project p : prj) {
			logger.info(i++ + "th " + p.toPrettyJsonString() );
		}
	}	
	
	@Test
	public void getProject() throws IOException, ConfigurationException {
		ProjectService prjService = new ProjectService();
		
		Project prj = prjService.getProjectDetail("TEST");
		
		logger.info("P=" + prj.toPrettyJsonString());
	}	
}
