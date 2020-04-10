package org.netcracker.students.factories;

import org.netcracker.students.model.dto.JournalDTO;

import java.time.LocalDateTime;

public class JournalDTOFactory {
    public static JournalDTO createJournalDTO(int id, String name, String description, LocalDateTime creationDate) {
        return new JournalDTO(id, name, description, creationDate);
    }
}