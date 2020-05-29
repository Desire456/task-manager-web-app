package org.netcracker.students.controller;

import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.*;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.hibernate.HibernateManagerDAO;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.interfaces.ManagerDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.dao.postrgresql.PostgreSQLManagerDAO;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.dto.JournalDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to control the journals hiding the DAO layer
 * @see Journal
 */
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
        ManagerDAO ManagerDAO = PostgreSQLManagerDAO.getInstance();
        try {
            this.journalDAO = ManagerDAO.getJournalDao();
        } catch (SQLException e) {
            throw new GetConnectionException(DAOErrorConstants.GET_CONNECTION_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    /**
     * Get journal by id
     * @param id
     * @return
     * @throws ReadJournalException can't get journal by this id or problems with connection to DB
     */
    public Journal getJournal(int id) throws ReadJournalException {
        return this.journalDAO.read(id);
    }

    /**
     * Add journal if journal with the same name in database doesn't exist
     * @param journal
     * @throws CreateJournalException problems with connection to DB
     * @throws NameAlreadyExistException journal with the same name already exist
     */
    public void addJournal(Journal journal) throws CreateJournalException, NameAlreadyExistException {
        try {
            if (journalDAO.getByName(journal.getName(), journal.getUserId()) != null)
                throw new NameAlreadyExistException(String.format(DAOErrorConstants.NAME_ALREADY_EXIST_JOURNAL_EXCEPTION_MESSAGE,
                        journal.getName()));
        } catch (GetJournalByNameException e) {
            throw new CreateJournalException(DAOErrorConstants.CREATE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        journalDAO.create(journal.getName(), journal.getDescription(), journal.getUserId(),
                Timestamp.valueOf(journal.getCreationDate()), journal.getIsPrivate());
    }

    /**
     * Add journal with id if journal with the same name in database doesn't exist or with the same id doesn't
     * exist
     * @param journal
     * @throws CreateJournalException problems with connection to DB
     * @throws NameAlreadyExistException journal with the same name already exist
     * @throws JournalIdAlreadyExistException journal with the same id already exist
     */
    public void addJournalWithId(Journal journal) throws CreateJournalException, NameAlreadyExistException, JournalIdAlreadyExistException {
        try {
            if (journalDAO.getByName(journal.getName(), journal.getUserId()) != null)
                throw new NameAlreadyExistException(String.format(DAOErrorConstants.NAME_ALREADY_EXIST_JOURNAL_EXCEPTION_MESSAGE,
                        journal.getName()));
            if (journalDAO.read(journal.getId()) != null)
                throw new JournalIdAlreadyExistException(
                        DAOErrorConstants.JOURNAL_ID_ALREADY_EXIST_JOURNAL_EXCEPTION_MESSAGE + journal.getId());
        } catch (GetJournalByNameException | ReadJournalException e) {
            throw new CreateJournalException(DAOErrorConstants.CREATE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        journalDAO.create(journal.getId(), journal.getName(), journal.getDescription(), journal.getUserId(),
                Timestamp.valueOf(journal.getCreationDate()), journal.getIsPrivate());
    }

    /**
     * Get list of journals by ids
     * @param ids  ids of journals
     * @return
     * @throws ReadJournalException can't get journals by this ids or problem with connection to DB
     */
    public List<Journal> getJournals(List<Integer> ids) throws ReadJournalException {
        List<Journal> journals = new ArrayList<>();
        for (Integer i : ids) journals.add(this.getJournal(i));
        return journals;
    }

    /**
     * Delete journal by id
     * @param id id of journal
     * @throws DeleteJournalException can't delete journal by this id or problem with connection to DB
     */
    public void deleteJournal(int id) throws DeleteJournalException {
        journalDAO.delete(id);
    }

    /**
     * Delete journals by ids
     * @param ids ids of journals
     * @throws DeleteJournalException can't delete journals by this ids or problem with connection to DB
     */
    public void deleteJournal(String ids) throws DeleteJournalException {
        int id;
        String[] mas = ids.split(" ");
        for (String str : mas) {
            id = Integer.parseInt(str);
            this.deleteJournal(id);
        }
    }

    /**
     * Filter journals by LIKE pattern or only equal
     * @param userId id of user who own this journals
     * @param column filter by this column
     * @param pattern desired match
     * @param criteria ascending or descending
     * @param equal LIKE if false, EQUAL if true
     * @return list of DTO journals
     * @throws GetFilteredByEqualsJournalException can't filter by equal journals or problems with connection to DB
     * @throws GetFilteredByPatternJournalException can't filter by like journals or problems with connection to DB
     */
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

    /**
     * Update old journal to new journal
     * @param newJournal
     * @throws UpdateJournalException can't update to new journal or problems with connection to DB
     */
    public void editJournal(Journal newJournal) throws UpdateJournalException {
        journalDAO.update(newJournal);
    }

    /**
     * Get all journals by id of user
     * @param userId
     * @return list of DTO journals
     * @throws GetAllJournalByUserIdException can't get all journals by this userId or problems with connection
     * to DB
     */
    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        return journalDAO.getAll(userId);
    }
}