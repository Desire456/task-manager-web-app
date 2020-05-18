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

    public boolean checkXMLforXSD(String xmlPath) throws XmlValidatorException {
        try {
            File xml = new File(xmlPath);
            File xsd = new File(XmlValidatorConstants.PATH_TO_XSD);

            if (!xml.exists()) {
                throw new NotFoundXMLException(XmlValidatorConstants.XML_NOT_FOUND_MESSAGE +
                        xmlPath);
            }

            if (!xsd.exists()) {
                throw new NotFoundXSDException(XmlValidatorConstants.XSD_NOT_FOUND_MESSAGE +
                        XmlValidatorConstants.PATH_TO_XSD);
            }

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(XmlValidatorConstants.PATH_TO_XSD));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlPath));
            return true;
        } catch (NotFoundXMLException e) {
            throw new XmlValidatorException(e.getMessage());
        } catch (NotFoundXSDException e) {
            throw new XmlValidatorException(e.getMessage());
        } catch (SAXException | IOException e) {
            throw new XmlValidatorException(XmlValidatorConstants.XML_NOT_VERIFIED_MESSAGE + " " + e.getMessage());
        }

    }

    public boolean checkStringXMLforXSD(String xml) throws XmlValidatorException {
        try {
            File xsd = new File(XmlValidatorConstants.PATH_TO_XSD);

            if (!xsd.exists()) {
                throw new NotFoundXSDException(XmlValidatorConstants.XSD_NOT_FOUND_MESSAGE +
                        XmlValidatorConstants.PATH_TO_XSD);
            }

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(XmlValidatorConstants.PATH_TO_XSD));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
            return true;
        } catch (NotFoundXSDException e) {
            throw new XmlValidatorException(e.getMessage());
        } catch (SAXException | IOException e) {
            throw new XmlValidatorException(XmlValidatorConstants.XML_NOT_VERIFIED_MESSAGE + " " + e.getMessage());
        }

    }
}
