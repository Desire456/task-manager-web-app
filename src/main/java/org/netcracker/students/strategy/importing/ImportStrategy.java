package org.netcracker.students.strategy.importing;

import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

/**
 * Import strategy interface by type
 * @param <T> type of entity to import
 */
public interface ImportStrategy<T> {
    void store(T object) throws ImportException, PrintableImportException;
}
