package org.netcracker.students.strategy.importing;

public interface ImportStrategy<T> {
    void store(T object) throws ImportException;
}
