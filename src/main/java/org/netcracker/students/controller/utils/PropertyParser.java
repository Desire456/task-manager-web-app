package org.netcracker.students.controller.utils;

import org.netcracker.students.controller.ControllerConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyParser {
    private static PropertyParser instance;
    private final Properties property;

    private PropertyParser() throws PropertyFileException {
        try {
            FileInputStream stream = new FileInputStream(ControllerConstants.PATH_TO_PROPERTIES);
            this.property = new Properties();
            this.property.load(stream);
        } catch (IOException e) {
            throw new PropertyFileException(ControllerConstants.PROPERTY_FILE_EXCEPTION + e.getMessage());
        }
    }

    public static PropertyParser getInstance() throws PropertyFileException {
        if(instance == null) {
            instance = new PropertyParser();
        }
        return instance;
    }

    public String getProperty(String key) {
        return this.property.getProperty(key);
    }
}
