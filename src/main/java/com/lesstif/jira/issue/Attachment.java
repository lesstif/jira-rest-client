package com.lesstif.jira.issue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import lombok.EqualsAndHashCode;
import com.lesstif.jira.JsonPrettyString;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper=false)
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

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime created;
	
	private Integer size;
	private String mimeType;	
	private String content;
	private String thumbnail;
}
