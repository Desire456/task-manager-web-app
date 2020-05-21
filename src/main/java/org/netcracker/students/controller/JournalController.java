package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.*;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.hibernate.HibernateManagerDAO;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.dto.JournalDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JournalController {
    private static JournalController instance;

    public static synchronized JournalController getInstance() throws GetConnectionException {
        if (instance == null) {
            instance = new JournalController();
        }
        return instance;
    }

    private JournalDAO journalDAO;

    private JournalController() throws GetConnectionException {
        ManagerDAO ManagerDAO = HibernateManagerDAO.getInstance();
        try {
            this.journalDAO = ManagerDAO.getJournalDao();
        } catch (SQLException e) {
            throw new GetConnectionException(DAOErrorConstants.GET_CONNECTION_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    public Journal getJournal(int id) throws ReadJournalException {
        return this.journalDAO.read(id);
    }

    public void addJournal(Journal journal) throws CreateJournalException, NameAlreadyExistException {
        journalDAO.create(journal.getName(), journal.getDescription(), journal.getUserId(),
                Timestamp.valueOf(journal.getCreationDate()), journal.getIsPrivate());
    }

    public void addJournalWithId(Journal journal) throws CreateJournalException, NameAlreadyExistException, JournalIdAlreadyExistException {
        journalDAO.create(journal.getId(), journal.getName(), journal.getDescription(), journal.getUserId(),
                Timestamp.valueOf(journal.getCreationDate()), journal.getIsPrivate());
    }

    public List<Journal> getJournals(List<Integer> ids) throws ReadJournalException {
        List<Journal> journals = new ArrayList<>();
        for (Integer i : ids) journals.add(this.getJournal(i));
        return journals;
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

    public void editJournal(Journal newJournal) throws UpdateJournalException {
        journalDAO.update(newJournal);
    }

    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        return journalDAO.getAll(userId);
    }
}