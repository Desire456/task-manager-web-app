package org.netcracker.students.ejb.interfaces;

import org.netcracker.students.strategy.exporting.exceptions.ExportException;
import org.netcracker.students.strategy.exporting.exceptions.PrintableExportException;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

import javax.ejb.Local;
import java.util.List;

/**
 * Bean interface we'll be using to export and import data
 */
@Local
public interface IExportImport {
    /**
     * Method that exports journals and tasks
     *
     * @param journalIds journal ids list
     * @param tasksIds   task ids list
     * @return the marshalling xml
     * @throws ExportException          the common error exception
     * @throws PrintableExportException the important error exception
     */
    String exportObjects(List<Integer> journalIds, List<Integer> tasksIds) throws ExportException,
            PrintableExportException;

    /**
     * Method that export journals and tasks from xml
     *
     * @param xml    string containing journals and tasks
     * @param userId id of user needed for import journal to current user
     * @throws PrintableImportException the important error exception
     * @throws ImportException          the common error exception
     */
    void importObjects(String xml, int userId)
            throws PrintableImportException, ImportException;
}