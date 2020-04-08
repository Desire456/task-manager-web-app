package org.netcracker.students.factories;

import org.netcracker.students.model.Journal;

import java.time.LocalDateTime;

public class JournalFactory {
    public Journal createJournal(Journal journal) {
        return new Journal(journal);
    }

    public Journal createJournal(int id, String name, String description, int userId, LocalDateTime creatingDate,
                                 boolean accessModifier) {
        return new Journal(id, name, description, userId, creatingDate, accessModifier);
    }

    public Journal createJournal(String name, String description, int userId,
                                 LocalDateTime creatingDate, boolean accessModifier) {
        return new Journal(name, description, userId, creatingDate, accessModifier);
    }
}
