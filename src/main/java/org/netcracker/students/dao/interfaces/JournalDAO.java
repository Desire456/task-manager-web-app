package org.netcracker.students.dao.interfaces;


import org.netcracker.students.dao.exception.journalDAO.*;
import org.netcracker.students.model.Journal;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface JournalDAO {
    public Journal create(String name, String description, Integer userId,
                          Date creatingDate, String accessModifier) throws SQLException, CreateJournalException;

    public Journal read(int id) throws SQLException, ReadJournalException;

    public void update(Journal journal) throws SQLException, UpdateJournalException;

    public void delete(int id) throws SQLException, DeleteJournalException;

    public List<Journal> getAll() throws SQLException, GetAllJournalException;

    public List<Journal> getAll(int userId) throws SQLException, GetAllJournalByUserIdException;

    public List<Journal> getSortedByCriteria(String column, String criteria) throws SQLException, GetSortedByCriteriaJournalException;

    public List<Journal> getFilteredByPattern(String column, String pattern, String criteria) throws SQLException, GetFilteredByPatternJournalException;

    public List<Journal> getFilteredByEquals(String column, String equal, String criteria) throws SQLException, GetFilteredByEqualsJournalException;
}
