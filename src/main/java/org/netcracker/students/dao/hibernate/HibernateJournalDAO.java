package org.netcracker.students.dao.hibernate;

import org.netcracker.students.dao.exceptions.journalDAO.*;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.dto.JournalDTO;

import java.sql.Timestamp;
import java.util.List;

public class HibernateJournalDAO implements JournalDAO {
    @Override
    public Journal create(String name, String description, Integer userId, Timestamp creatingDate, boolean isPrivate) throws CreateJournalException {
        return null;
    }

    @Override
    public Journal read(int id) throws ReadJournalException {
        return null;
    }

    @Override
    public void update(Journal journal) throws UpdateJournalException {

    }

    @Override
    public void delete(int id) throws DeleteJournalException {

    }

    @Override
    public List<JournalDTO> getAll() throws GetAllJournalException {
        return null;
    }

    @Override
    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        return null;
    }

    @Override
    public List<JournalDTO> getSortedByCriteria(int userId, String column, String criteria) throws GetSortedByCriteriaJournalException {
        return null;
    }

    @Override
    public List<JournalDTO> getFilteredByPattern(int userId, String column, String pattern, String criteria) throws GetFilteredByPatternJournalException {
        return null;
    }

    @Override
    public List<JournalDTO> getFilteredByEquals(int userId, String column, String equal, String criteria) throws GetFilteredByEqualsJournalException {
        return null;
    }
}
