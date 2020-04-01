package org.netcracker.students.factories;

import org.netcracker.students.dto.JournalDTO;

import java.time.LocalDate;

public class JournalDTOFactory {
    public JournalDTO createJournalDTO(int id, String name, String description, LocalDate creationDate) {
        return new JournalDTO(id, name, description, creationDate);
    }
}
