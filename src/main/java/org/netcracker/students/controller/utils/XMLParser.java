package org.netcracker.students.controller.utils;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringWriter;

import static org.netcracker.students.controller.utils.XMLConstants.*;

public class XMLParser {

    private static XMLParser instance;

    public static synchronized XMLParser getInstance() {
        if (instance == null) {
            instance = new XMLParser();
        }
        return instance;
    }

    private XMLParser() {
    }


    public String toXML(Object obj) throws ParseXMLException {
        StringWriter sw = new StringWriter();
        if (obj != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(obj.getClass());
                Marshaller marshaller = context.createMarshaller();
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(new StreamSource(PATH_TO_XSD));
                marshaller.setSchema(schema);
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(obj, sw);
            } catch (JAXBException | SAXException e) {
                throw new ParseXMLException(PARSE_EXCEPTION_MESSAGE + e.getMessage());
            }
        } else throw new NullPointerException(NULL_EXCEPTION_MESSAGE);
        return sw.toString();
    }
}
