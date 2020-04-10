package org.netcracker.students.controller.utils.xmlQueryValidator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLQueryValidator {

    private static XMLQueryValidator instance;

    private XMLQueryValidator(){
    }

    public static synchronized XMLQueryValidator getInstance(){
        if (instance == null)
            instance = new XMLQueryValidator();
        return instance;
    }

    public boolean checkXMLforXSD() throws XMLQueryValidatorException {
        try {
            File xml = new File(XMLQueryValidatorConstants.PATH_TO_XML);
            File xsd = new File(XMLQueryValidatorConstants.PATH_TO_XSD);

            if (!xml.exists()) {
                throw new NotFoundXMLException(XMLQueryValidatorConstants.XML_NOT_FOUND_MESSAGE +
                        XMLQueryValidatorConstants.PATH_TO_XML);
            }

            if (!xsd.exists()) {
                throw new NotFoundXSDException(XMLQueryValidatorConstants.XSD_NOT_FOUND_MESSAGE +
                        XMLQueryValidatorConstants.PATH_TO_XSD);
            }

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(XMLQueryValidatorConstants.PATH_TO_XSD));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(XMLQueryValidatorConstants.PATH_TO_XML));
            return true;
        }
        catch(NotFoundXMLException | NotFoundXSDException e){
            throw new XMLQueryValidatorException(e.getMessage());
        } catch (SAXException | IOException e) {
            throw new XMLQueryValidatorException(XMLQueryValidatorConstants.XML_NOT_VERIFIED_MESSAGE +" "+ e.getMessage());
        }

    }
}
