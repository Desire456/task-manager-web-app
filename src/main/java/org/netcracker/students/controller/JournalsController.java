package org.netcracker.students.controller;

import org.netcracker.students.controller.utils.IdGenerator;
import org.netcracker.students.model.Journal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class JournalsController {
    private static JournalsController instance;

    public static synchronized JournalsController getInstance() {
        if (instance == null) {
            instance = new JournalsController();
        }
        return instance;
    }

    private HashMap<Integer, Journal> journals;
    private TasksController tasksController;

    private JournalsController() {
        this.journals = new HashMap<>();
        this.tasksController = TasksController.getInstance();
    }

    public Journal getJournal(int id) {
        return this.journals.get(id);
    }

    public void addJournal(Journal journal) {
        int id = IdGenerator.getInstance().getId();
        this.journals.put(id, journal);
        this.tasksController.addJournal(id, journal);
    }

    public void removeJournal(int id) {
        this.journals.remove(id);
        this.tasksController.deleteJournal(id);
    }

    public void removeJournal(String ids) {
        int id = 0;
        for (int i = 0; i < ids.length() - 1; i += 2) {
            id = Character.getNumericValue(ids.charAt(i));
            this.removeJournal(id + 1);
            this.tasksController.deleteJournal(id);
        }
    }


    public void changeJournal(int id, Journal newJournal) {
        this.journals.put(id, newJournal);
        this.tasksController.deleteJournal(id);
        this.tasksController.addJournal(id, newJournal);
    }


    public List<Journal> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(this.journals.values()));
    }
}
