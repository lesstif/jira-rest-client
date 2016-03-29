package com.lesstif.jira.issue;

import com.lesstif.jira.JsonPrettyString;
import java.util.List;
import lombok.Data;

@Data
public class IssueSearchResult extends JsonPrettyString{
    
  private String expand;
  private Integer startAt;
  private Integer maxResults;
  private Integer total;
  private List<Issue> issues;
}
