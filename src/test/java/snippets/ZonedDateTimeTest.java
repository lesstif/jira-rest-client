package snippets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesstif.jira.issue.Attachment;
import com.lesstif.jira.issue.Issue;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZonedDateTimeTest {

    @Test
    public void aTest() throws ParseException {
        ZonedDateTime zd = ZonedDateTime.now();

        String dateStr = "2021-11-22T15:01:02.000+0900";

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date parsedDate = dtf.parse(dateStr);

        System.out.println("odt" + parsedDate);
    }

    @Test
    public void testZonedDateTimeSerializer() throws IOException, ConfigurationException {
        String jsonContent = "{\n" +
                "    \"self\": \"https://jira.lesstif.com/rest/api/2/attachment/14402\",\n" +
                "    \"filename\": \"bug-description.pdf\",\n" +
                "    \"author\": {\n" +
                "        \"self\": \"https://jira.lesstif.com/rest/api/2/user?username=lesstif\",\n" +
                "        \"key\": \"lesstif\",\n" +
                "        \"name\": \"lesstif\",\n" +
                "        \"avatarUrls\": {\n" +
                "            \"48x48\": \"https://jira.lesstif.com/secure/useravatar?avatarId=10414\",\n" +
                "            \"24x24\": \"https://jira.lesstif.com/secure/useravatar?size=small&avatarId=10414\",\n" +
                "            \"16x16\": \"https://jira.lesstif.com/secure/useravatar?size=xsmall&avatarId=10414\",\n" +
                "            \"32x32\": \"https://jira.lesstif.com/secure/useravatar?size=medium&avatarId=10414\"\n" +
                "        },\n" +
                "        \"displayName\": \"정광섭\",\n" +
                "        \"active\": true\n" +
                "    },\n" +
                "    \"created\": \"2021-11-22T15:01:02.000+0900\",\n" +
                "    \"size\": 594326,\n" +
                "    \"mimeType\": \"application/pdf\",\n" +
                "    \"properties\": {},\n" +
                "    \"content\": \"https://jira.lesstif.com/secure/attachment/14402/bug-description.pdf\"\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        TypeReference<Attachment> ref = new TypeReference<Attachment>() {
        };
        Attachment attachment = mapper.readValue(jsonContent, ref);

        assertThat(attachment.getCreated(), notNullValue());
        assertThat(attachment.getContent(), notNullValue());
        assertThat(attachment.getSize(), greaterThan(10));
    }
}
