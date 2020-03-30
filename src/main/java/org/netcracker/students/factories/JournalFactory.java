package org.netcracker.students.factories;

import org.netcracker.students.model.Journal;

import java.time.LocalDateTime;

public class JournalFactory {
    public Journal createJournal(Journal journal) {
        return new Journal(journal);
    }

    public Journal createJournal(int id, String name, String accessModifier, LocalDateTime creationDate, String description) {
        return new Journal(id, name, accessModifier, creationDate, description);
    }
}
