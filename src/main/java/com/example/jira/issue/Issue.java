package com.example.jira.issue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.example.jira.JsonPrettyString;
import com.sun.jersey.core.util.Base64;

import lombok.Data;

@Data
public class Issue extends JsonPrettyString{
	private String expand;
	private String id;
	private String self;
	private String key;
	
	private IssueFields fields;

	public void addAttachment(String filePath) throws IOException {		
		addAttachment(new File(filePath));
	}

	public void addAttachment(File file) throws IOException {
		if (file.exists()) {
			Attachment attachment = new Attachment();
			attachment.setContentData(Base64.encode(FileUtils.readFileToByteArray(file)));
			
			fields.addAttachment(attachment);
		}		
	}
}
