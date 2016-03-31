package com.lesstif.jira.issue;

import com.lesstif.jira.JsonPrettyString;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties({"avatarUrls"
})
public class Author extends JsonPrettyString {
	@org.codehaus.jackson.annotate.JsonProperty("emailAddress")
	private java.lang.String emailaddress;

 	public void setEmailaddress(java.lang.String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public java.lang.String getEmailaddress() {
		return emailaddress;
	}

	@org.codehaus.jackson.annotate.JsonProperty("self")
	private java.lang.String self;

 	public void setSelf(java.lang.String self) {
		this.self = self;
	}

	public java.lang.String getSelf() {
		return self;
	}

	@org.codehaus.jackson.annotate.JsonProperty("displayName")
	private java.lang.String displayname;

 	public void setDisplayname(java.lang.String displayname) {
		this.displayname = displayname;
	}

	public java.lang.String getDisplayname() {
		return displayname;
	}

	@org.codehaus.jackson.annotate.JsonProperty("active")
	private java.lang.Boolean active;

 	public void setActive(java.lang.Boolean active) {
		this.active = active;
	}

	public java.lang.Boolean getActive() {
		return active;
	}

	@org.codehaus.jackson.annotate.JsonProperty("name")
	private java.lang.String name;

 	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getName() {
		return name;
	}

}