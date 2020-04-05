package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.dao.exception.journalDAO.*;
import org.netcracker.students.dao.exception.taskDAO.DeleteTaskException;
import org.netcracker.students.dao.interfaces.DAOManager;
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
        String RETURN_JOURNAL_SQL = "SELECT * FROM journals WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setTimestamp(4, creationDate);
            preparedStatement.setBoolean(5, isPrivate);
            preparedStatement.execute();
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(RETURN_JOURNAL_SQL)) {
                preparedStatement1.setString(1, name);
                JournalFactory journalFactory = new JournalFactory();
                ResultSet resultSet = preparedStatement1.executeQuery();
                if (resultSet.next()) {
                    return journalFactory.createJournal(resultSet.getInt(1), resultSet.getString(3),
                            resultSet.getString(4), resultSet.getInt(2),
                            resultSet.getTimestamp(5).toLocalDateTime(), isPrivate);
                }
            }
        } catch (SQLException e) {
            throw new CreateJournalException(DAOErrorConstants.CREATE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        return null;
    }

    @Override
    public Journal read(int id) throws ReadJournalException {
        String sql = "SELECT * FROM journals WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                JournalFactory journalFactory = new JournalFactory();
                return journalFactory.createJournal(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(2),
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getBoolean(6));
            }
        } catch (SQLException e) {
            throw new ReadJournalException(DAOErrorConstants.READ_JOURNAL_EXCEPTION + e.getMessage());
        }
        return null;
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
    public void delete(int id) throws DeleteJournalException, SQLException, DeleteTaskException {
        String sql = "DELETE FROM journals WHERE journal_id = ?";
        DAOManager daoManager = PostgreSQLDAOManager.getInstance();
        daoManager.getTasksDao().deleteByJournalId(id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DeleteJournalException(DAOErrorConstants.DELETE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<Journal> getAll() throws GetAllJournalException {
        String sql = "SELECT * FROM journals";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Journal> journals = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            JournalFactory journalFactory = new JournalFactory();
            while (resultSet.next()) {
                Journal journal = journalFactory.createJournal(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(2),
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getBoolean(6));
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetAllJournalException(DAOErrorConstants.GET_ALL_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        String sql = "SELECT * FROM journals WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            List<JournalDTO> journals = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            JournalDTOFactory journalDTOFactory = new JournalDTOFactory();
            while (resultSet.next()) {
                JournalDTO journal = journalDTOFactory.createJournalDTO(resultSet.getInt(1), resultSet.getString(3),
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
    public List<Journal> getSortedByCriteria(String column, String criteria) throws GetSortedByCriteriaJournalException {
        String sql = "SELECT * FROM journals ORDER BY ? ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, column);
            preparedStatement.setString(2, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Journal> journals = new ArrayList<Journal>();
            JournalFactory journalFactory = new JournalFactory();
            while (resultSet.next()) {
                Journal journal = journalFactory.createJournal(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(2),
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getBoolean(6));
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetSortedByCriteriaJournalException(DAOErrorConstants.GET_SORTED_BY_CRITERIA_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }


    @Override
    public List<Journal> getFilteredByPattern(String column, String pattern, String criteria) throws GetFilteredByPatternJournalException {
        String sql = "SELECT * FROM journals WHERE ? LIKE ? ORDER BY ? ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, column);
            preparedStatement.setString(2, pattern);
            preparedStatement.setString(3, column);
            preparedStatement.setString(4, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Journal> journals = new ArrayList<Journal>();
            JournalFactory journalFactory = new JournalFactory();
            while (resultSet.next()) {
                Journal journal = journalFactory.createJournal(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(2),
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getBoolean(6));
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetFilteredByPatternJournalException(DAOErrorConstants.GET_FILTERED_BY_PATTERN_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<Journal> getFilteredByEquals(String column, String equal, String criteria) throws GetFilteredByEqualsJournalException {
        String sql = "SELECT * FROM journals WHERE ? = ? ORDER BY ? ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, column);
            preparedStatement.setString(2, equal);
            preparedStatement.setString(3, column);
            preparedStatement.setString(4, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Journal> journals = new ArrayList<>();
            JournalFactory journalFactory = new JournalFactory();
            while (resultSet.next()) {
                Journal journal = journalFactory.createJournal(resultSet.getInt(1), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(2),
                        resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getBoolean(6));
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new GetFilteredByEqualsJournalException(DAOErrorConstants.GET_FILTERED_BY_EQUALS_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
