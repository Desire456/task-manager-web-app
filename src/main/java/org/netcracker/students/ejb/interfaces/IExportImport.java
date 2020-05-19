package org.netcracker.students.ejb.interfaces;

import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.exporting.exceptions.ExportException;
import org.netcracker.students.strategy.exporting.exceptions.PrintableExportException;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IExportImport {
    String exportObjects(List<Integer> journalIds, List<Integer> tasksIds) throws ExportException,
            PrintableExportException;

    void importObjects(List<Journal> journals, List<Task> tasks, String xml, int userId)
            throws PropertyFileException, PrintableImportException, ImportException;
}