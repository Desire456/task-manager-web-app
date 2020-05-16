package org.netcracker.students.ejb.interfaces;

import org.netcracker.students.ejb.exceptions.ExportException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IExportImport {
    void exportObjects(List<Integer> journalIds, List<Integer> tasksIds) throws ExportException;

    void importObjects(String xml);

    String asd();
}