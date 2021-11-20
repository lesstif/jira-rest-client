package com.lesstif.jira.issue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lesstif.jira.JsonPrettyString;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class WorklogElement extends JsonPrettyString implements Comparable<WorklogElement> {

    private static final String TAG = WorklogElement.class.getName();

    @JsonProperty("updateAuthor")
    private Author updateauthor;
    @JsonProperty("timeSpentSeconds")
    private Integer timeSpentSeconds;
    @JsonProperty("started")
    private String started;
    @JsonProperty("self")
    private String self;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("timeSpent")
    private String timespent;
    @JsonProperty("author")
    private Author author;
    @JsonProperty("id")
    private String id;
    @JsonProperty("created")
    private String created;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("issueId")
    private String issueId;

    public WorklogElement() {

    }

    public WorklogElement(Issue sue) {
        self = sue.getId();
    }

    public void startedDate(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        final String format = dateFormat.format(date);
        final String dateFinal = format + "T04:22:37.471+0000";
        started = dateFinal;
    }

    public Date startedDate() {
        Date date = null;
        try {
            final String formatDate = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            DateFormat df = new SimpleDateFormat(formatDate);
            date = df.parse(started);
        } catch (ParseException ex) {
            Logger.getLogger(TAG).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return date;
    }

    @Override
    public int compareTo(WorklogElement o) {
        final String idString = this.id;
        final String idStringOther = o.id;

        final Integer currentId = Integer.valueOf(idString);
        final Integer outID = Integer.valueOf(idStringOther);

        return currentId.compareTo(outID);

    }

    public String issueIdValue() {
        Pattern pattern = Pattern.compile("issue\\/(.*)\\/work");
        Matcher matcher = pattern.matcher(self);
        String issueId = null;
        if (matcher.find()) {
            issueId = matcher.group(1);
        }
        
        return issueId;
    }

}
