package org.netcracker.students.strategy.exporting;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.TaskController;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExportList {
    private List<Journal> journals;
    private List<Task> tasks;

    public ExportList() {
    }

    public void addJournals(List<Integer> ids) throws GetConnectionException, ReadJournalException {
        JournalController journalController = JournalController.getInstance();
        this.journals = new ArrayList<>(journalController.getJournals(ids));
    }

    public void addTasks(List<Integer> ids) throws GetConnectionException, ReadTaskException {
        TaskController taskController = TaskController.getInstance();
        this.tasks = new ArrayList<>(taskController.getTasks(ids));
    }

    public void addTasksByJournalId(List<Integer> ids) throws GetAllTaskException, GetConnectionException {
        TaskController taskController = TaskController.getInstance();
        this.tasks = new ArrayList<>(taskController.getTasksByJournalIds(ids));
    }

    public List<Journal> getJournals() {
        return Collections.unmodifiableList(this.journals);
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }
}