package org.netcracker.students.controller;

import org.netcracker.students.dao.exception.journalDAO.*;
import org.netcracker.students.dao.exception.taskDAO.DeleteTaskException;
import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.postrgresql.PostgreSQLDAOManager;
import org.netcracker.students.dto.JournalDTO;
import org.netcracker.students.model.Journal;

import java.sql.SQLException;
import java.sql.Timestamp;
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

    public Journal getJournal(int id) throws ReadJournalException {
        return this.journalDAO.read(id);
    }

    public void addJournal(Journal journal) throws CreateJournalException {
        journalDAO.create(journal.getName(), journal.getDescription(), journal.getUserId(),
                Timestamp.valueOf(journal.getCreationDate()), journal.getAccessModifier());
    }

    public void deleteJournal(int id) throws DeleteJournalException, DeleteTaskException, SQLException {
        journalDAO.delete(id);
    }

    public void deleteJournal(String ids) throws DeleteJournalException, DeleteTaskException, SQLException {
        int id;
        for (int i = 0; i < ids.length() - 1; i += 2) {
            id = Character.getNumericValue(ids.charAt(i));
            this.deleteJournal(id);
        }
    }

    public void changeJournal(Journal newJournal) throws UpdateJournalException {
        journalDAO.update(newJournal);
    }

    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        return journalDAO.getAll(userId);
    }
}
