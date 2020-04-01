package org.netcracker.students.factories;

import org.netcracker.students.model.Journal;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JournalFactory {
    public Journal createJournal(Journal journal) {
        return new Journal(journal);
    }

    public Journal createJournal(int id, String name, String description, int userId,
                                 LocalDate creatingDate, String accessModifier) {
        return new Journal(id, name, description, userId, creatingDate, accessModifier);
    }

    public Journal createJournal(String name, String description, int userId,
                                 LocalDate creatingDate, String accessModifier) {
        return new Journal(name, description, userId, creatingDate, accessModifier);
    }
}
