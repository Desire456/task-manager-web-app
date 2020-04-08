package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.journalDAO.*;
import org.netcracker.students.dao.exceptions.taskDAO.DeleteTaskException;
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

    private JournalDAO journalDAO;

    private JournalsController() {
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
                Timestamp.valueOf(journal.getCreationDate()), journal.getIsPrivate());
    }

    public void deleteJournal(int id) throws DeleteJournalException {
        journalDAO.delete(id);
    }

    public void deleteJournal(String ids) throws DeleteJournalException {
        int id;
        String[] mas = ids.split(" ");
        for (String str : mas) {
            id = Integer.parseInt(str);
            this.deleteJournal(id);
        }
    }

    public List<JournalDTO> getFilteredJournals(int userId, String column, String pattern, String criteria, boolean equal)
            throws GetFilteredByEqualsJournalException, GetFilteredByPatternJournalException {
        if (equal) {
            return journalDAO.getFilteredByEquals(userId, column, pattern, criteria);
        } else {
            String likePattern = ControllerConstants.LIKE_PATTERN_CONSTANT +
                    pattern + ControllerConstants.LIKE_PATTERN_CONSTANT;
            return journalDAO.getFilteredByPattern(userId,
                    column, likePattern, criteria);
        }
    }

    public void changeJournal(Journal newJournal) throws UpdateJournalException {
        journalDAO.update(newJournal);
    }

    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        return journalDAO.getAll(userId);
    }
}