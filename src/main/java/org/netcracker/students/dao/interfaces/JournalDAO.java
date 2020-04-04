package org.netcracker.students.dao.interfaces;

import org.netcracker.students.dao.exception.journalDAO.*;
import org.netcracker.students.dao.exception.taskDAO.DeleteTaskException;
import org.netcracker.students.dto.JournalDTO;
import org.netcracker.students.model.Journal;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface JournalDAO {
    public Journal create(String name, String description, Integer userId,
                          Timestamp creatingDate, String accessModifier) throws CreateJournalException;

    public Journal read(int id) throws ReadJournalException;

    public void update(Journal journal) throws UpdateJournalException;

    public void delete(int id) throws DeleteJournalException, SQLException, DeleteTaskException;

    public List<Journal> getAll() throws GetAllJournalException;

    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException;

    public List<Journal> getSortedByCriteria(String column, String criteria) throws GetSortedByCriteriaJournalException;

    public List<Journal> getFilteredByPattern(String column, String pattern, String criteria) throws GetFilteredByPatternJournalException;

    public List<Journal> getFilteredByEquals(String column, String equal, String criteria) throws GetFilteredByEqualsJournalException;
}
