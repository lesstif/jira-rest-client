package com.example.jira.issue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import com.example.jira.JsonPrettyString;
import com.sun.jersey.core.util.Base64;

@Data
/**
 * @see https://docs.atlassian.com/jira/REST/latest/#d2e4213
 * 
 * @author lesstif
 *
 */
public class Attachment extends JsonPrettyString{
	private String id;
	private String self;
	private String filename;
	private Reporter author;
	
	private DateTime created;
	
	private Integer size;
	private String mimeType;
	private String content;
	private String thumbnail;
	
	@JsonIgnore
	private byte[] contentData;
	
	public void addAttachment(File file) throws IOException {
		if (file.exists()) {
			contentData = Base64.encode(FileUtils.readFileToByteArray(file));
		}		
	}	
	
}
