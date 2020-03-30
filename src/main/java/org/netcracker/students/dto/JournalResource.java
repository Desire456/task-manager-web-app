package org.netcracker.students.dto;

import java.util.List;

public class JournalResource {
    private List<JournalTO> journals;

    public JournalResource(List<JournalTO> journals) {
        this.journals = journals;
    }

    public List<JournalTO> getAllJournals() {
        return journals;
    }

    public void save(JournalTO journal) {
        journals.add(journal);
    }

    public void delete(String journalId) {
        journals.removeIf(journal -> journal.getId().equals(journalId));
    }
}
