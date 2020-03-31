package org.netcracker.students.dao.postrgresql;

import org.apache.bcel.generic.RET;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.factories.JournalFactory;
import org.netcracker.students.model.Journal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLJournalDAO implements JournalDAO {
    private Connection connection;

    public PostgreSQLJournalDAO (Connection connection){
        this.connection = connection;
    }

    @Override
    public Journal create(String name, String description, Integer userId, Date creationDate, String accessModifier)
            throws SQLException {
        String sql = "INSERT INTO journals VALUES (default, ?, ?, ?, ?, ?)";
        String RETURN_JOURNAL_SQL = "SELECT * FROM journals WHERE name = ?";
        boolean privateFlag = false;
        if (accessModifier.equals("private")) privateFlag = true;
        else if(accessModifier.equals("public")) privateFlag = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setDate(4, creationDate);
            preparedStatement.setBoolean(5, privateFlag);
            preparedStatement.execute();
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(RETURN_JOURNAL_SQL)){
            preparedStatement.setString(1,name);
            JournalFactory journalFactory = new JournalFactory();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return journalFactory.createJournal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
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
            if (resultSet.getBoolean(6)) accessModifier = "private";
            else accessModifier = "public";
            JournalFactory journalFactory = new JournalFactory();
            if (resultSet.next()){
                return journalFactory.createJournal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
            }
        }
        return null;
    }

    @Override
    public void update(Journal journal) throws SQLException {
        String sql = "UPDATE journals SET private_flag = ?, name = ?, creation_date = ?, description = ? WHERE journal_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            boolean isPrivate;
            isPrivate = journal.getAccessModifier().equals("private");
            preparedStatement.setBoolean(1,isPrivate);
            preparedStatement.setString(2, journal.getName());
            preparedStatement.setDate(3, Date.valueOf(journal.getCreationDate()));
            preparedStatement.setString(4, journal.getDescription());
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM journals WHERE journal_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
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
            JournalFactory journalFactory = new JournalFactory();
            while(resultSet.next()){
                if (resultSet.getBoolean(6)) accessModifier = "private";
                else accessModifier = "public";
                Journal journal = journalFactory.createJournal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
                journals.add(journal);
            }
            return journals;
        }
    }

    @Override
    public List<Journal> getAll(int journalId) throws SQLException {
        String sql = "SELECT * FROM journals WHERE journal_id";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, journalId);
            List<Journal> journals = new ArrayList<Journal>();
            ResultSet resultSet = preparedStatement.executeQuery();
            String accessModifier;
            JournalFactory journalFactory = new JournalFactory();
            while(resultSet.next()){
                if (resultSet.getBoolean(6)) accessModifier = "private";
                else accessModifier = "public";
                Journal journal = journalFactory.createJournal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
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
            JournalFactory journalFactory = new JournalFactory();
            while(resultSet.next()){
                if (resultSet.getBoolean(6)) accessModifier = "private";
                else accessModifier = "public";
                Journal journal = journalFactory.createJournal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
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
           JournalFactory journalFactory = new JournalFactory();
           while(resultSet.next()){
               if (resultSet.getBoolean(6)) accessModifier = "Private";
               else accessModifier = "Public";
               Journal journal = journalFactory.createJournal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
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
            JournalFactory journalFactory = new JournalFactory();
            while(resultSet.next()){
                if (resultSet.getBoolean(6)) accessModifier = "private";
                else accessModifier = "public";
                Journal journal = journalFactory.createJournal(resultSet.getInt(1),resultSet.getString(3), accessModifier,
                        resultSet.getDate(5).toLocalDate().atStartOfDay(),resultSet.getString(4));
                journals.add(journal);
            }
            return journals;
        }
    }
}
