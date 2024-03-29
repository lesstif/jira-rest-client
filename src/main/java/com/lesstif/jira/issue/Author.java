package com.lesstif.jira.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lesstif.jira.JsonPrettyString;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({"avatarUrls"
})
public class Author extends JsonPrettyString {
	@JsonProperty("emailAddress")
	private java.lang.String emailaddress;

 	public void setEmailaddress(java.lang.String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public java.lang.String getEmailaddress() {
		return emailaddress;
	}

	@JsonProperty("self")
	private java.lang.String self;

 	public void setSelf(java.lang.String self) {
		this.self = self;
	}

	public java.lang.String getSelf() {
		return self;
	}

	@JsonProperty("displayName")
	private java.lang.String displayname;

 	public void setDisplayname(java.lang.String displayname) {
		this.displayname = displayname;
	}

	public java.lang.String getDisplayname() {
		return displayname;
	}

	@JsonProperty("active")
	private java.lang.Boolean active;

 	public void setActive(java.lang.Boolean active) {
		this.active = active;
	}

	public java.lang.Boolean getActive() {
		return active;
	}

	@JsonProperty("name")
	private java.lang.String name;

 	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getName() {
		return name;
	}

        @JsonProperty("key")
        private java.lang.String key;

        public void setKey(java.lang.String key) {
          this.key = key;
        }

        public java.lang.String getKey() {
                return key;
        }

        @JsonProperty("timeZone")
        private java.lang.String timeZone;

        public void setTimeZone(java.lang.String timeZone) {
          this.timeZone = timeZone;
        }

        public java.lang.String getTimeZone() {
                return timeZone;
        }

}
