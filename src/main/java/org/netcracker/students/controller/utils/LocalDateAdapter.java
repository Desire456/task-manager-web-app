package org.netcracker.students.controller.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public LocalDate unmarshal(String xml) throws Exception {
        return LocalDate.parse(xml, formatter);
    }

    @Override
    public String marshal(LocalDate object) throws Exception {
        return object.format(formatter);
    }
}
