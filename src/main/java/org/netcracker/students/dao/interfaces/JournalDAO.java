package org.netcracker.students.dao.interfaces;


import org.netcracker.students.dto.JournalDTO;
import org.netcracker.students.model.Journal;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface JournalDAO {
    public Journal create(String name, String description, Integer userId,
                          Date creatingDate, String accessModifier) throws SQLException;

    public Journal read(int id) throws SQLException;

    public void update(Journal journal) throws SQLException;

    public void delete(int id) throws SQLException;

    public List<Journal> getAll() throws SQLException;

    public List<JournalDTO> getAll(int userId) throws SQLException;

    public List<Journal> getSortedByCriteria(String column, String criteria) throws SQLException;

    public List<Journal> getFilteredByPattern(String column, String pattern, String criteria) throws SQLException;

    public List<Journal> getFilteredByEquals(String column, String equal, String criteria) throws SQLException;
}
