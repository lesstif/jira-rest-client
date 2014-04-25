package com.example.jira;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JsonPrettyString {
	
	final public String toPrettyJsonString() {
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, this);
		} catch (IOException e) {
			return toString();
		}
		
		return sw.toString();
	}
}
