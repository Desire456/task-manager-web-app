package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.dao.exception.taskDAO.*;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.factories.TaskFactory;
import org.netcracker.students.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLTasksDAO implements TasksDAO {
    private Connection connection;

    public PostgreSQLTasksDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Task create(String name, String status, String description, Date plannedDate, Date dateOfDone, Integer journalId) throws CreateTaskException {
        String sql = "INSERT INTO tasks VALUES (default, ?, ?, ?, ?, ?, ?)";
        String RETURN_CREATED_TASK_SQL = "SELECT * FROM tasks WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, status);
            preparedStatement.setDate(5, plannedDate);
            preparedStatement.setDate(6, dateOfDone);
            preparedStatement.execute();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(RETURN_CREATED_TASK_SQL)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            TaskFactory taskFactory = new TaskFactory();
            if (resultSet.next()) {
                return taskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                            resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime(),
                        resultSet.getString(5));
            }
        } catch (SQLException e) {
            throw new CreateTaskException(DAOErrorConstants.CREATE_TASK_EXCEPTION_MESSAGE);
        }
        return null;
    }

    @Override
    public Task read(int id) throws ReadTaskException {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            TaskFactory taskFactory = new TaskFactory();
            if (resultSet.next()) {
                return taskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getDate(7).toLocalDate(),
                        resultSet.getString(5));
            }
        } catch (SQLException e) {
            throw new ReadTaskException(DAOErrorConstants.READ_TASK_EXCEPTION_MESSAGE);
        }
        return null;
    }

    @Override
    public void update(Task task) throws UpdateTaskException {
        String sql = "UPDATE tasks SET name = ?, description = ?, planned_date = ?, dateOfDone = ?, status = ? WHERE " +
                "task_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getPlannedDate()));
            preparedStatement.setTimestamp(4, task.getDateOfDone() == null ? null : Timestamp.valueOf(task.getDateOfDone()));
            preparedStatement.setString(5, task.getStatus());
            preparedStatement.setInt(6, task.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new UpdateTaskException(DAOErrorConstants.UPDATE_TASK_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void delete(int id) throws DeleteTaskException {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DeleteTaskException(DAOErrorConstants.DELETE_TASK_EXCEPTION_MESSAGE);
        }

    }

    @Override
    public List<Task> getAll() throws GetAllTaskException {
        String sql = "SELECT * FROM tasks";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Task> tasks = new ArrayList<Task>();
            ResultSet resultSet = preparedStatement.executeQuery();
            TaskFactory taskFactory = new TaskFactory();
            while (resultSet.next()) {
                Task task =taskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime(),
                        resultSet.getString(7));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public List<Task> getAll(int journalId) throws GetAllTaskException {
        String sql = "SELECT * FROM tasks WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, journalId);
            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            TaskFactory taskFactory = new TaskFactory();
            while (resultSet.next()) {
                Task task = taskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime(),
                        resultSet.getString(7));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public List<Task> getSortedByCriteria(int journalId, String column, String criteria) throws GetSortedByCriteriaTaskException {
        String sql = "Select * FROM tasks JOIN journals ON tasks.journal_id = journals.journal_id WHERE journal_id = ? ORDER BY ? ?";
        //String sql = "Select * FROM tasks JOIN journals ON tasks.journal_id = journals.journal_id WHERE (journal_id=?) AND (? = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, column);
            preparedStatement.setString(3, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> tasks = new ArrayList<Task>();
            TaskFactory taskFactory = new TaskFactory();
            while (resultSet.next()) {
                Task task = taskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime(),
                        resultSet.getTimestamp(6).toLocalDateTime(), resultSet.getString(7));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetSortedByCriteriaTaskException(DAOErrorConstants.GET_SORTED_BY_CRITERIA_TASK_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public List<Task> getFilteredByPattern(int journalId, String column, String pattern, String criteria) throws GetFilteredByPatternTaskException {
        String sql = "SELECT * FROM tasks JOIN journals ON tasks.journal_id = journals.journal_id WHERE (journal_id = ?)" +
                " AND (? LIKE ?) ORDER BY ? ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, column);
            preparedStatement.setString(3, pattern);
            preparedStatement.setString(4, column);
            preparedStatement.setString(5, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            TaskFactory taskFactory = new TaskFactory();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                Task task = taskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime(),
                        resultSet.getTimestamp(6).toLocalDateTime(), resultSet.getString(7));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetFilteredByPatternTaskException(DAOErrorConstants.GET_FILTERED_BY_PATTERN_TASK_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public List<Task> getFilteredByEquals(int journalId, String column, String equal, String criteria) throws GetFilteredByEqualsTaskException {
        String sql = "SELECT * FROM tasks JOIN journals ON tasks.journal_id = journals.journal_id WHERE (journal_id = ?) AND (? = ?) ORDER BY ? ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, column);
            preparedStatement.setString(3, equal);
            preparedStatement.setString(4, column);
            preparedStatement.setString(5, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            TaskFactory taskFactory = new TaskFactory();
            while (resultSet.next()) {
                Task task = taskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime(),
                        resultSet.getTimestamp(6).toLocalDateTime(), resultSet.getString(7));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetFilteredByEqualsTaskException(DAOErrorConstants.GET_FILTERED_BY_EQUALS_TASK_EXCEPTION_MESSAGE);
        }
    }
}
