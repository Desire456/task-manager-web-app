package org.netcracker.students.ejb.interfaces;

import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.strategy.exporting.exceptions.ExportException;
import org.netcracker.students.strategy.exporting.exceptions.PrintableExportException;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IExportImport {
    String exportObjects(List<Integer> journalIds, List<Integer> tasksIds) throws ExportException, PrintableExportException;
    void importObjects(String xml) throws PropertyFileException, PrintableImportException, ImportException;
}