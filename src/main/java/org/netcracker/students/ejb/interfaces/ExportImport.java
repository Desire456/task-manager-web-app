package org.netcracker.students.ejb.interfaces;

import org.netcracker.students.ejb.exceptions.ExportException;

import java.util.List;

public interface ExportImport {
    public void exportObjects(List<Integer> journalIds, List<Integer> tasksIds) throws ExportException;

    public void importObjects(String xml);
}