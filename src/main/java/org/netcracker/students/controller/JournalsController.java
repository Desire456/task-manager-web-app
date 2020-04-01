package org.netcracker.students.controller;

import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.postrgresql.PostgreSQLDAOManager;
import org.netcracker.students.model.Journal;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class JournalsController {
    private static JournalsController instance;

    public static synchronized JournalsController getInstance() {
        if (instance == null) {
            instance = new JournalsController();
        }
        return instance;
    }

    private TasksController tasksController;
    private JournalDAO journalDAO;

    private JournalsController() {
        this.tasksController = TasksController.getInstance();
        DAOManager DAOManager = PostgreSQLDAOManager.getInstance();
        try {
            this.journalDAO = DAOManager.getJournalDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Journal getJournal(int id) throws SQLException {
        return this.journalDAO.read(id);
    }

    public void addJournal(Journal journal) throws SQLException {
        Journal newJournal = journalDAO.create(journal.getName(), journal.getDescription(), journal.getUserId(),
                Date.valueOf(journal.getCreationDate()), journal.getAccessModifier());
        //this.tasksController.addJournal(newJournal.getId(), newJournal);
    }

    public void deleteJournal(int id) throws SQLException {
        journalDAO.delete(id);
       // this.tasksController.deleteJournal(id);
    }

    public void deleteJournal(String ids) throws SQLException {
        int id;
        for (int i = 0; i < ids.length() - 1; i += 2) {
            id = Character.getNumericValue(ids.charAt(i));
            this.deleteJournal(id);
            //this.tasksController.deleteJournal(id);
        }
    }


    public void changeJournal(Journal newJournal) throws SQLException {
        journalDAO.update(newJournal);
        int journalId = newJournal.getId();
        //this.tasksController.deleteJournal(journalId);
        //this.tasksController.addJournal(journalId, newJournal);
    }


    public List<Journal> getAll(int userId) throws SQLException {
        return journalDAO.getAll(userId);
    }
}
