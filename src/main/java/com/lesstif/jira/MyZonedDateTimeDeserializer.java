package com.lesstif.jira;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

/**
 * REST API 의 Date time 포맷이 "2021-11-22T15:01:02.000+0900" 로 날라와서 파싱이 안 되서 만든 Deserializer.
 *
 */
public class MyZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @SneakyThrows
    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext) throws IOException {

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date parsedDate = dtf.parse(jsonParser.getText());

        return ZonedDateTime.ofInstant(parsedDate.toInstant(),
                ZoneId.systemDefault());
    }
}