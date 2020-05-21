package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.taskDAO.*;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.factories.TaskDTOFactory;
import org.netcracker.students.factories.TaskFactory;
import org.netcracker.students.model.Task;
import org.netcracker.students.model.dto.TaskDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLTaskDAO implements TasksDAO {
    private Connection connection;

    public PostgreSQLTaskDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Task create(String name, String status, String description, Timestamp plannedDate, Timestamp dateOfDone, Integer journalId) throws CreateTaskException, NameAlreadyExistException {
        String sql = "INSERT INTO tasks VALUES (default, ?, ?, ?, ?, ?, ?)";
        Task task;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            task = getByName(name, journalId);
            if (task != null) throw new CreateTaskException();
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, status);
            preparedStatement.setTimestamp(5, plannedDate);
            preparedStatement.setTimestamp(6, dateOfDone);
            preparedStatement.execute();
            task = getByName(name, journalId);
        } catch (SQLException e) {
            throw new CreateTaskException(DAOErrorConstants.CREATE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        } catch (CreateTaskException e) {
            throw new NameAlreadyExistException(String.format(DAOErrorConstants.NAME_ALREADY_EXIST_TASK_EXCEPTION_MESSAGE,
                    name));
        }
        return task;
    }

    @Override
    public Task create(int id, String name, String status, String description, Timestamp plannedDate, Timestamp dateOfDone, Integer journalId) throws CreateTaskException, NameAlreadyExistException, TaskIdAlreadyExistException {
        String sql = "INSERT INTO tasks VALUES (?, ?, ?, ?, ?, ?, ?)";
        Task task;
        try {
            task = read(id);
            if (task != null) throw new CreateTaskWithIdException();
            task = getByName(name, journalId);
            if (task != null) throw new CreateTaskException();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, journalId);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, description);
                preparedStatement.setString(5, status);
                preparedStatement.setTimestamp(6, plannedDate);
                preparedStatement.setTimestamp(7, dateOfDone);
                preparedStatement.execute();
                task = getByName(name, journalId);
            }
        } catch (SQLException | ReadTaskException e) {
            throw new CreateTaskException(DAOErrorConstants.CREATE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        } catch (CreateTaskException e) {
            throw new NameAlreadyExistException(String.format(DAOErrorConstants.NAME_ALREADY_EXIST_TASK_EXCEPTION_MESSAGE,
                    name));
        } catch (CreateTaskWithIdException e) {
            throw new TaskIdAlreadyExistException(DAOErrorConstants.TASK_ID_ALREADY_EXIST_EXCEPTION_MESSAGE + id);
        }
        return task;
    }

    private Task getByName(String name, int journalId) throws SQLException {
        String RETURN_CREATED_TASK_SQL = "SELECT * FROM tasks WHERE (name = ?) AND (journal_id = ?)";
        Task task = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(RETURN_CREATED_TASK_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, journalId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = TaskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime(),
                        resultSet.getString(5));
            }
        }
        return task;
    }

    @Override
    public Task read(int id) throws ReadTaskException {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        Task task = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = TaskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime(),
                        resultSet.getString(5));
            }
        } catch (SQLException e) {
            throw new ReadTaskException(DAOErrorConstants.READ_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        return task;
    }

    @Override
    public void update(Task task) throws UpdateTaskException {
        String sql = "UPDATE tasks SET name = ?, description = ?, planned_date = ?, date_of_done = ?, status = ? WHERE " +
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
            throw new UpdateTaskException(DAOErrorConstants.UPDATE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void deleteByTaskId(int id) throws DeleteTaskException {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DeleteTaskException(DAOErrorConstants.DELETE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<TaskDTO> getAll() throws GetAllTaskException {
        String sql = "SELECT * FROM tasks";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<TaskDTO> tasks = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasks.add(TaskDTOFactory.createTaskDTO(resultSet.getInt(1),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime()));
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<TaskDTO> getAll(int journalId) throws GetAllTaskException {
        String sql = "SELECT * FROM tasks WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, journalId);
            List<TaskDTO> tasks = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasks.add(TaskDTOFactory.createTaskDTO(resultSet.getInt(1),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime()));
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<Task> getAllByJournalId(int journalId) throws GetAllTaskException {
        String sql = "SELECT * FROM tasks WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, journalId);
            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasks.add(TaskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ?
                                null : resultSet.getTimestamp(7).toLocalDateTime(),
                        resultSet.getString(5)));
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<Task> getAllByNameAndJournalId(String name, int journalId) throws GetAllTaskException {
        String sql = "SELECT * FROM tasks WHERE (journal_id = ?) && (name = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, name);
            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasks.add(TaskFactory.createTask(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ?
                                null : resultSet.getTimestamp(7).toLocalDateTime(),
                        resultSet.getString(5)));
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
    }


    @Override
    public List<TaskDTO> getSortedByCriteria(int journalId, String column, String criteria) throws GetSortedByCriteriaTaskException {
        String sql = "SELECT * FROM tasks WHERE journal_id = ? " +
                "ORDER BY %s %s";
        String SQL = String.format(sql, column, criteria);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, journalId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TaskDTO> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(TaskDTOFactory.createTaskDTO(resultSet.getInt(1),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime()));
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetSortedByCriteriaTaskException(DAOErrorConstants.GET_SORTED_BY_CRITERIA_TASK_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }

    @Override
    public List<TaskDTO> getFilteredByPattern(int journalId, String column, String pattern, String criteria) throws GetFilteredByPatternTaskException {
        String sql = "SELECT * FROM tasks WHERE (journal_id = ?) AND (%s LIKE ?) " +
                "ORDER BY %s %s";
        String SQL = String.format(sql, column, column, criteria);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, pattern);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TaskDTO> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(TaskDTOFactory.createTaskDTO(resultSet.getInt(1),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime()));
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetFilteredByPatternTaskException(DAOErrorConstants.GET_FILTERED_BY_PATTERN_TASK_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }

    @Override
    public List<TaskDTO> getFilteredByEquals(int journalId, String column, String equal, String criteria) throws GetFilteredByEqualsTaskException {
        String sql = "SELECT * FROM tasks WHERE (journal_id = ?) AND (%s = ?) " +
                "ORDER BY %s %s";
        String SQL = String.format(sql, column, column, criteria);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, equal);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TaskDTO> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(TaskDTOFactory.createTaskDTO(resultSet.getInt(1),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getTimestamp(6).toLocalDateTime(),
                        resultSet.getTimestamp(7) == null ? null : resultSet.getTimestamp(7).toLocalDateTime()));
            }
            return tasks;
        } catch (SQLException e) {
            throw new GetFilteredByEqualsTaskException(DAOErrorConstants.GET_FILTERED_BY_EQUALS_TASK_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }
}
