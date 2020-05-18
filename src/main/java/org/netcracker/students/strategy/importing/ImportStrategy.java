package org.netcracker.students.strategy.importing;

import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

public interface ImportStrategy<T> {
    void store(T object) throws ImportException, PrintableImportException;
}
