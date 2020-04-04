package org.netcracker.students.controller.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public LocalDateTime unmarshal(String xml) throws Exception {
        return LocalDateTime.parse(xml, formatter);
    }

    @Override
    public String marshal(LocalDateTime object) throws Exception {
        return object.format(formatter);
    }
}
