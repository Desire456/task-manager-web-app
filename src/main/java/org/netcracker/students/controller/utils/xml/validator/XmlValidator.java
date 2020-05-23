package org.netcracker.students.controller.utils.xml.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XmlValidator {

    private static XmlValidator instance;

    private XmlValidator() {
    }

    public static synchronized XmlValidator getInstance() {
        if (instance == null)
            instance = new XmlValidator();
        return instance;
    }

    public boolean checkStringXMLforXSD(String xml) throws XmlValidatorException {
        try {
            File xsd = new File(XmlValidatorConstants.PATH_TO_XSD);

            if (!xsd.exists()) {
                throw new NotFoundXSDException(XmlValidatorConstants.XSD_NOT_FOUND_MESSAGE +
                        XmlValidatorConstants.PATH_TO_XSD);
            }
            validateXml(xml);
            return true;
        } catch (NotFoundXSDException e) {
            throw new XmlValidatorException(e.getMessage());
        } catch (SAXException | IOException e) {
            throw new XmlValidatorException(XmlValidatorConstants.XML_NOT_VERIFIED_MESSAGE + " " + e.getMessage());
        }

    }

    private void validateXml(String xml) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(XmlValidatorConstants.PATH_TO_XSD));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new StringReader(xml)));
    }

    private void validateXml(String xml, String xsd) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(new StringReader(xsd)));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new StringReader(xml)));
    }

    public boolean checkStringXMLforXSD(String xml, String xsd) throws XmlValidatorException {
        try {
            validateXml(xml, xsd);
            return true;
        } catch (SAXException | IOException e) {
            throw new XmlValidatorException(XmlValidatorConstants.XML_NOT_VERIFIED_MESSAGE + " " + e.getMessage());
        }
    }
}
