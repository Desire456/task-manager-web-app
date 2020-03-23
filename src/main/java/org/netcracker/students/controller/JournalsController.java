package org.netcracker.students.controller;

import org.netcracker.students.controller.utils.IdGenerator;
import org.netcracker.students.model.Journal;


import java.util.ArrayList;
import java.util.HashMap;

public class JournalsController {
    private static JournalsController instance;

    public static synchronized JournalsController getInstance() {
        if (instance == null) {
            instance = new JournalsController();
        }
        return instance;
    }

    private HashMap<Integer, Journal> journals;
    private ControllersGetter controllersGetter;

    private JournalsController() {
        this.journals = new HashMap<>();
        this.controllersGetter = ControllersGetter.getInstance();
    }

    public Journal getJournal(int id) {
        return this.journals.get(id);
    }

    public void addJournal(Journal journal) {
        int id = IdGenerator.getInstance().getId();
        this.journals.put(id, journal);
        this.controllersGetter.addController(new TasksController(id, journal));
    }

    public void removeJournal(int id) {
        this.journals.remove(id);
        this.controllersGetter.removeController(id);
    }

    public void removeJournal(String ids) {
        int id = 0;
        for(int i = 0; i < ids.length() - 1; i+=2) {
            id = Character.getNumericValue(ids.charAt(i));
            this.removeJournal(id + 1);
        }
    }



    public void changeJournal(int id, Journal newJournal) {
        this.journals.put(id, newJournal);
        this.controllersGetter.removeController(id);
        this.controllersGetter.addController(new TasksController(id, newJournal));
    }


    public ArrayList<Journal> getAll() {
        return new ArrayList<>(this.journals.values());
    }
}
