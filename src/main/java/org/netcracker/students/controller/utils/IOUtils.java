package org.netcracker.students.controller.utils;


import org.netcracker.students.exceptions.BackupFileException;
import org.netcracker.students.exceptions.PropertyParserInitException;

import javax.xml.bind.JAXBException;

public interface IOUtils {
    public void serializeObject(Object obj) throws BackupFileException, PropertyParserInitException, JAXBException;

    public Object deserializeObject() throws ClassNotFoundException, PropertyParserInitException, BackupFileException;
}
