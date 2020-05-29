package org.netcracker.students.strategy.exporting;

import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class contains list of journal and tasks to export
 */
public class ExportList {
    private final List<Journal> journals;
    private final List<Task> tasks;

    public ExportList() {
        this.journals = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public void addJournals(List<Journal> journals) {
        this.journals.addAll(this.journals.size(), journals);
    }

    public void addTasks(List<Task> tasks) {
        this.tasks.addAll(this.tasks.size(), tasks);
    }

    public List<Journal> getJournals() {
        return Collections.unmodifiableList(this.journals);
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }
}