package org.netcracker.students.dao.postrgresql;

import org.apache.bcel.generic.RET;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.model.Journal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLJournalDAO implements JournalDAO {
    private Connection connection;

    public PostgreSQLJournalDAO (){
    }

    @Override
    public Journal create(String name, String description, Integer userId, Date creationDate, boolean privateFlag) throws SQLException {
        String sql = "INSERT INTO journals VALUES (?, ?, ?, ?, ?)";
        String RETURN_JOURNAL_SQL = "SELECT * FROM journals WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setBoolean(2, privateFlag);
            preparedStatement.setString(3, name);
            preparedStatement.setDate(4, creationDate);
            preparedStatement.setString(5, description);
            preparedStatement.execute();
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(RETURN_JOURNAL_SQL)){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            String accessModifier;
            if (resultSet.getBoolean(6)) accessModifier = "Private";
            else accessModifier = "Public";
            if (resultSet.next()){
                Journal journal = new Journal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
                return journal;
            }
        }
        return null;
    }

    @Override
    public Journal read(int id) throws SQLException {
        String sql = "SELECT * FROM journals WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            String accessModifier;
            if (resultSet.getBoolean(6)) accessModifier = "Private";
            else accessModifier = "Public";
            if (resultSet.next()){
                Journal journal = new Journal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
                return journal;
            }
        }
        return null;
    }

    @Override
    public void update(Journal journal) throws SQLException {
        String sql = "UPDATE journals SET private_flag = ?, name = ?, creation_date = ?, description = ? WHERE journal_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            boolean isPrivate;
            if (journal.getAccessModifier()=="Private") isPrivate = true;
            else isPrivate = false;
            preparedStatement.setBoolean(1,isPrivate);
            preparedStatement.setString(2, journal.getName());
            preparedStatement.setDate(3, Date.valueOf(journal.getCreationDate().toLocalDate()));
            preparedStatement.setString(4, journal.getDescription());
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(Journal journal) throws SQLException {
        String sql = "DELETE FROM journals WHERE journal_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, journal.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public List<Journal> getAll() throws SQLException {
        String sql = "SELECT * FROM journals";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            List<Journal> journals = new ArrayList<Journal>();
            ResultSet resultSet = preparedStatement.executeQuery();
            String accessModifier;
            while(resultSet.next()){
                if (resultSet.getBoolean(6)) accessModifier = "Private";
                else accessModifier = "Public";
                Journal journal = new Journal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
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
            String accessModifier;
            while(resultSet.next()){
                if (resultSet.getBoolean(6)) accessModifier = "Private";
                else accessModifier = "Public";
                Journal journal = new Journal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
                journals.add(journal);
            }
            return journals;
        }
    }

    @Override
    public List<Journal> getFilteredByPattern(String column, String pattern, String criteria) throws SQLException {
       String sql = "SELECT * FROM journals WHERE ? LIKE ? ORDER BY ? ?";
       try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
           preparedStatement.setString(1,column);
           preparedStatement.setString(2, pattern);
           preparedStatement.setString(3, column);
           preparedStatement.setString(4, criteria);
           ResultSet resultSet = preparedStatement.executeQuery();
           List<Journal> journals = new ArrayList<Journal>();
           String accessModifier;
           while(resultSet.next()){
               if (resultSet.getBoolean(6)) accessModifier = "Private";
               else accessModifier = "Public";
               Journal journal = new Journal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                       resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
               journals.add(journal);
           }
           return journals;
       }
    }

    @Override
    public List<Journal> getFilteredByEquals(String column, String equal, String criteria) throws SQLException {
        String sql = "SELECT * FROM journals WHERE ? = ? ORDER BY ? ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,column);
            preparedStatement.setString(2, equal);
            preparedStatement.setString(3, column);
            preparedStatement.setString(4, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Journal> journals = new ArrayList<Journal>();
            String accessModifier;
            while(resultSet.next()){
                if (resultSet.getBoolean(6)) accessModifier = "Private";
                else accessModifier = "Public";
                Journal journal = new Journal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
                journals.add(journal);
            }
            return journals;
        }
    }
}
