package org.netcracker.students.controller.utils;


import org.netcracker.students.exceptions.BackupFileException;
import org.netcracker.students.exceptions.PropertyParserInitException;

import javax.xml.bind.JAXBException;

public class Backupper {
    public static final String BIN = "binary";
    public static final String XML = "xml";

    private IOUtils ioUtils;

    public Backupper() {
    }

    public void backupFunction(Object object, String current) throws PropertyParserInitException, BackupFileException, JAXBException {
        choose(current);
        ioUtils.serializeObject(object);
    }

    public Object restoreFunction(String current) throws ClassNotFoundException, PropertyParserInitException, BackupFileException {
        choose(current);
        return ioUtils.deserializeObject();
    }


    private void choose(String current) {
        if (current.equals(BIN)) {
            ioUtils = BinarySerializer.getInstance();
        }
    }
}
