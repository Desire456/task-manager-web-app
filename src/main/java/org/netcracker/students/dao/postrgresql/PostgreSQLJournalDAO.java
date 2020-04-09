package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.dao.exceptions.journalDAO.*;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dto.JournalDTO;
import org.netcracker.students.factories.JournalDTOFactory;
import org.netcracker.students.factories.JournalFactory;
import org.netcracker.students.model.Journal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLJournalDAO implements JournalDAO {
    private Connection connection;

    public PostgreSQLJournalDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Journal create(String name, String description, Integer userId, Timestamp creationDate,
                          boolean isPrivate) throws CreateJournalException {
        String sql = "INSERT INTO journals VALUES (default, ?, ?, ?, ?, ?)";
        String RETURN_JOURNAL_SQL = "SELECT * FROM journals WHERE (name = ?) AND (user_id = ?)";
        Journal journal = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setTimestamp(4, creationDate);
            preparedStatement.setBoolean(5, isPrivate);
            preparedStatement.execute();
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(RETURN_JOURNAL_SQL)) {
                preparedStatement1.setString(1, name);
                preparedStatement1.setInt(2, userId);
                ResultSet resultSet = preparedStatement1.executeQuery();
                if (resultSet.next()) {
                    journal = JournalFactory.createJournal(resultSet.getInt(1), resultSet.getString(3),
                            resultSet.getString(4), resultSet.getInt(2),
                            resultSet.getTimestamp(5).toLocalDateTime(), isPrivate);
                }
            }
        } catch (SQLException e) {
            throw new CreateJournalException(DAOErrorConstants.CREATE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        return journal;
    }

    @Override
    public Journal read(int id) throws ReadJournalException {
        String sql = "SELECT * FROM journals WHERE journal_id = ?";
        Journal journal = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                journal = JournalFactory.createJournal(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(2),
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getBoolean(6));
            }
        } catch (SQLException e) {
            throw new ReadJournalException(DAOErrorConstants.READ_JOURNAL_EXCEPTION + e.getMessage());
        }
        return journal;
    }

    @Override
    public void update(Journal journal) throws UpdateJournalException {
        String sql = "UPDATE journals SET isprivate = ?, name = ?, creation_date = ?, description = ? WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(5, journal.getId());
            preparedStatement.setBoolean(1, journal.getIsPrivate());
            preparedStatement.setString(2, journal.getName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(journal.getCreationDate()));
            preparedStatement.setString(4, journal.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateJournalException(DAOErrorConstants.UPDATE_JOURNAL_EXCEPTION + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DeleteJournalException {
        String sql = "DELETE  FROM journals WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DeleteJournalException(DAOErrorConstants.DELETE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<JournalDTO> getAll() throws GetAllJournalException {
        String sql = "SELECT * FROM journals";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<JournalDTO> journals = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                JournalDTO journal = JournalDTOFactory.createJournalDTO(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetAllJournalException(DAOErrorConstants.GET_ALL_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        String sql = "SELECT * FROM journals WHERE (user_id = ?) OR (isprivate = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setBoolean(2, false);
            List<JournalDTO> journals = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                JournalDTO journal = JournalDTOFactory.createJournalDTO(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetAllJournalByUserIdException(DAOErrorConstants.GET_ALL_JOURNAL_BY_USER_ID_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<JournalDTO> getSortedByCriteria(int userId, String column, String criteria)
            throws GetSortedByCriteriaJournalException {
        String sql = "SELECT * FROM journals WHERE (user_id = ?) " +
                "OR (isprivate = ?) ORDER BY %s %s";
        String SQL = String.format(sql, column, criteria);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<JournalDTO> journals = new ArrayList<>();
            while (resultSet.next()) {
                JournalDTO journal = JournalDTOFactory.createJournalDTO(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetSortedByCriteriaJournalException(DAOErrorConstants.GET_SORTED_BY_CRITERIA_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }


    @Override
    public List<JournalDTO> getFilteredByPattern(int userId, String column, String pattern, String criteria) throws GetFilteredByPatternJournalException {
        String sql = "SELECT * FROM journals WHERE ((user_id = ?) OR (isprivate = ?)) AND (%s LIKE ?) " +
                "ORDER BY %s %s";
        String SQL = String.format(sql, column, column, criteria);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setBoolean(2, false);
            preparedStatement.setString(3, pattern);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<JournalDTO> journals = new ArrayList<>();
            while (resultSet.next()) {
                JournalDTO journal = JournalDTOFactory.createJournalDTO(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetFilteredByPatternJournalException(DAOErrorConstants.GET_FILTERED_BY_PATTERN_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<JournalDTO> getFilteredByEquals(int userId, String column, String equal, String criteria)
            throws GetFilteredByEqualsJournalException {
        String sql = "SELECT * FROM journals  WHERE ((user_id = ?) " +
                "OR (isprivate = ?)) AND (%s = ?) ORDER BY %s %s";
        String SQL = String.format(sql, column, column, criteria);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setBoolean(2, false);
            preparedStatement.setString(3, equal);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<JournalDTO> journals = new ArrayList<>();
            while (resultSet.next()) {
                JournalDTO journal = JournalDTOFactory.createJournalDTO(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetFilteredByEqualsJournalException(DAOErrorConstants.GET_FILTERED_BY_EQUALS_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
