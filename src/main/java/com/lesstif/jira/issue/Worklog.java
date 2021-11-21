package com.lesstif.jira.issue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lesstif.jira.JsonPrettyString;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class Worklog extends JsonPrettyString {
	@JsonProperty("startAt")
	private java.lang.Integer startat;

 	public void setStartat(java.lang.Integer startat) {
		this.startat = startat;
	}

	public java.lang.Integer getStartat() {
		return startat;
	}

	@JsonProperty("maxResults")
	private java.lang.Integer maxresults;

 	public void setMaxresults(java.lang.Integer maxresults) {
		this.maxresults = maxresults;
	}

	public java.lang.Integer getMaxresults() {
		return maxresults;
	}

	@JsonProperty("total")
	private java.lang.Integer total;

 	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}

	public java.lang.Integer getTotal() {
		return total;
	}

	@JsonProperty("worklogs")
	private WorklogElement[] worklogs;

 	public void setWorklogs(WorklogElement[] worklogs) {
		this.worklogs = worklogs;
	}

	public WorklogElement[] getWorklogs() {
		return worklogs;
	}

}