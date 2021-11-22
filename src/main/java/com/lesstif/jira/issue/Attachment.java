package com.lesstif.jira.issue;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.lesstif.jira.MyZonedDateTimeDeserializer;
import lombok.Data;

import lombok.EqualsAndHashCode;
import com.lesstif.jira.JsonPrettyString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @see https://docs.atlassian.com/jira/REST/latest/#d2e4213
 *
 * @author lesstif
 *
 */
@EqualsAndHashCode(callSuper=false)
@Data
@JsonIgnoreProperties({"properties"})
public class Attachment extends JsonPrettyString{	
	private String id;
	private String self;
	private String filename;
	private Reporter author;

	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonDeserialize(using = MyZonedDateTimeDeserializer.class)
	private ZonedDateTime created;
	
	private Integer size;
	private String mimeType;	
	private String content;
	private String thumbnail;
}
