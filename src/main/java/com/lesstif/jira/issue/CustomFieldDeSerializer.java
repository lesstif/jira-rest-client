package com.lesstif.jira.issue;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// JIRA Custom Field Serialize
public class CustomFieldDeSerializer extends JsonDeserializer<Object> {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Object deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		logger.info("Deserialize...");

		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		
		for (int i = 0; i < node.size(); i++)
		{
			JsonNode child = node.get(i);
			
			if (child == null) {
				logger.info(i + "th Child node is null");
				continue;
			}
			//String
			if (child.isTextual()) {

				// FIXME
				/*
				Iterator<String> it = child.get("FieldNames");
				while (it.hasNext()) {
					String field = it.next();
					logger.info("in while loop " + field);
					if (field.startsWith("customfield")) {
						
					}
				}
				 */
			}
		}
		return null;
	}

}
