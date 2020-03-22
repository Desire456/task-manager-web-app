package org.netcracker.students.controller.utils;

import org.netcracker.students.exceptions.PropertyParserInitException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XMLConverter {

    private static XMLConverter instance;
    private static final String NULL_EXCEPTION_MESSAGE = "The object you want to convert to XML is null";


    public static synchronized XMLConverter getInstance() {
        if (instance == null) {
            instance = new XMLConverter();
        }
        return instance;
    }

    private XMLConverter() {
    }


    public String toXML(Object obj) {
        StringWriter sw = new StringWriter();
        if (obj != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(obj.getClass());
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(obj, sw);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        } else throw new NullPointerException(NULL_EXCEPTION_MESSAGE);
        return sw.toString();
    }
}
