package org.netcracker.students.controller.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XMLParser {

    private static XMLParser instance;
    private static final String NULL_EXCEPTION_MESSAGE = "The object you want to convert to XML is null";


    public static synchronized XMLParser getInstance() {
        if (instance == null) {
            instance = new XMLParser();
        }
        return instance;
    }

    private XMLParser() {
    }


    public String toXML(Object obj) {
        StringWriter sw = new StringWriter();
        if (obj != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(obj.getClass());
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(obj, sw);
            } catch (JAXBException ignored) {
                // todo we can not marshal to xml then we will return empty string ;( and we will not know that it is breakage ;(((
            }
        } else throw new NullPointerException(NULL_EXCEPTION_MESSAGE);
        return sw.toString();
    }
}
