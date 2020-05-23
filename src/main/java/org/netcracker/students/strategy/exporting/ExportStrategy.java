package org.netcracker.students.strategy.exporting;

import org.netcracker.students.strategy.exporting.exceptions.ExportException;

import java.util.List;

/**
 * Export strategy interface
 */
public interface ExportStrategy {
    void collectExportIds(ExportList exportList, List<Integer> ids) throws ExportException;
}
