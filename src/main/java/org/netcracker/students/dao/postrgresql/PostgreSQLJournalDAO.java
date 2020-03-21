package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.controller.utils.JournalIdGenerator;
import org.netcracker.students.controller.utils.TaskIdGenerator;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.entity.Journal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLJournalDAO implements JournalDAO {
    private Connection connection;

    public PostgreSQLJournalDAO (){

    }

    @Override
    public Journal create(String name, String description, Integer userId, Date creationDate, boolean privateFlag) throws SQLException {
        String sql = "INSERT INTO journals VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,JournalIdGenerator.getInstance().getId());
            preparedStatement.setInt(2, userId);
            preparedStatement.setBoolean(3, privateFlag);
            preparedStatement.setString(4, name);
            preparedStatement.setDate(5, creationDate);
            preparedStatement.setString(6, description);
            preparedStatement.execute();
        }
        return null;
    }

    @Override
    public Journal read(int id) throws SQLException {
        String sql = "SELECT * FROM journals WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Journal journal = new Journal(resultSet.getInt(1), resultSet.getString( 4), resultSet.getString(6),
                        resultSet.getBoolean(3), resultSet.getDate(5), resultSet.getInt(2));
                return journal;
            }
        }
        return null;
    }

    @Override
    public void update(Journal journal) throws SQLException {
        String sql = "UPDATE journals SET private_flag = ?, name = ?, creation_date = ?, description = ? WHERE journal_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setBoolean(1, journal.getPrivateFlag());
            preparedStatement.setString(2, journal.getName());
            preparedStatement.setDate(3, journal.getCreationDate());
            preparedStatement.setString(4, journal.getDescription());
            preparedStatement.setInt(5, journal.getJournalId());
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(Journal journal) throws SQLException {
        String sql = "DELETE FROM journals WHERE journal_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, journal.getJournalId());
            preparedStatement.execute();
        }
    }

    @Override
    public List<Journal> getAll() throws SQLException {
        String sql = "SELECT * FROM journals";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            List<Journal> journals = new ArrayList<Journal>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Journal journal = new Journal(resultSet.getInt(1), resultSet.getString( 4), resultSet.getString(6),
                        resultSet.getBoolean(3), resultSet.getDate(5), resultSet.getInt(2));
                journals.add(journal);
            }
            return journals;
        }
    }

    @Override
    public List<Journal> getSortedByCriteria(String column, String criteria) throws SQLException {
        String sql = "SELECT * FROM journals ORDER BY ? ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, column);
            preparedStatement.setString(2, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Journal> journals = new ArrayList<Journal>();
            while(resultSet.next()){
                Journal journal = new Journal(resultSet.getInt(1), resultSet.getString( 4), resultSet.getString(6),
                        resultSet.getBoolean(3), resultSet.getDate(5), resultSet.getInt(2));
                journals.add(journal);
            }
            return journals;
        }
    }

    @Override
    public List<Journal> getFilteredByPattern(String column, String pattern, String criteria) throws SQLException {
        return null; //todo доделать это и то, что ниже (тут я не разобрался до конца, что требуется, плавит слегка)
    }

    @Override
    public List<Journal> getFilteredByEquals(String column, String equal, String criteria) throws SQLException {
        return null;
    }
}
