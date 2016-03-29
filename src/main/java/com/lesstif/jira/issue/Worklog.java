package com.lesstif.jira.issue;

import com.lesstif.jira.JsonPrettyString;
import lombok.Data;

@Data
public class Worklog extends JsonPrettyString {
	@org.codehaus.jackson.annotate.JsonProperty("startAt")
	private java.lang.Integer startat;

 	public void setStartat(java.lang.Integer startat) {
		this.startat = startat;
	}

	public java.lang.Integer getStartat() {
		return startat;
	}

	@org.codehaus.jackson.annotate.JsonProperty("maxResults")
	private java.lang.Integer maxresults;

 	public void setMaxresults(java.lang.Integer maxresults) {
		this.maxresults = maxresults;
	}

	public java.lang.Integer getMaxresults() {
		return maxresults;
	}

	@org.codehaus.jackson.annotate.JsonProperty("total")
	private java.lang.Integer total;

 	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}

	public java.lang.Integer getTotal() {
		return total;
	}

	@org.codehaus.jackson.annotate.JsonProperty("worklogs")
	private WorklogElement[] worklogs;

 	public void setWorklogs(WorklogElement[] worklogs) {
		this.worklogs = worklogs;
	}

	public WorklogElement[] getWorklogs() {
		return worklogs;
	}

}