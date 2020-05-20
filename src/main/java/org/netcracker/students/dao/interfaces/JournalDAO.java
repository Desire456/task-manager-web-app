package org.netcracker.students.dao.interfaces;

import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.*;
import org.netcracker.students.model.dto.JournalDTO;
import org.netcracker.students.model.Journal;

import java.sql.Timestamp;
import java.util.List;

public interface JournalDAO {
    Journal create(String name, String description, Integer userId,
                   Timestamp creatingDate, boolean isPrivate) throws CreateJournalException, NameAlreadyExistException;

    Journal read(int id) throws ReadJournalException;

    void update(Journal journal) throws UpdateJournalException;

    void delete(int id) throws DeleteJournalException;

    List<JournalDTO> getAll() throws GetAllJournalException;

    List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException;

    List<JournalDTO> getSortedByCriteria(int userId, String column, String criteria)
            throws GetSortedByCriteriaJournalException;

    List<JournalDTO> getFilteredByPattern(int userId, String column, String pattern, String criteria)
            throws GetFilteredByPatternJournalException;

    List<JournalDTO> getFilteredByEquals(int userId, String column, String equal, String criteria)
            throws GetFilteredByEqualsJournalException;
}
