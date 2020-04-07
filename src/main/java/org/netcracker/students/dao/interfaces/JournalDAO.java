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
                          Timestamp creatingDate, boolean isPrivate) throws CreateJournalException;

    public Journal read(int id) throws ReadJournalException;

    public void update(Journal journal) throws UpdateJournalException;

    public void delete(int id) throws DeleteJournalException, SQLException, DeleteTaskException;

    public List<JournalDTO> getAll() throws GetAllJournalException;

    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException;

    public List<JournalDTO> getSortedByCriteria(int userId, String column, String criteria) throws GetSortedByCriteriaJournalException;

    public List<JournalDTO> getFilteredByPattern(int userId, String column, String pattern, String criteria)
            throws GetFilteredByPatternJournalException;

    public List<JournalDTO> getFilteredByEquals(int userId, String column, String equal, String criteria)
            throws GetFilteredByEqualsJournalException;
}
