package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.controller.utils.TaskIdGenerator;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.entity.Task;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PostgreSQLTasksDAO implements TasksDAO {
    private Connection connection;

    public PostgreSQLTasksDAO(Connection connection){
        this.connection = connection;
    }



    @Override
    public Task create(String name, String status, String description, Date plannedDate, Date dateOfDone, Integer journalId) throws SQLException {
        String sql = "INSERT INTO tasks VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, TaskIdGenerator.getInstance().getId());
            preparedStatement.setInt(2,journalId);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,description);
            preparedStatement.setDate(5, plannedDate);
            preparedStatement.setDate(6, dateOfDone);
            preparedStatement.execute();
        }
        return null;
    }

    @Override
    public Task read(int id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Task task = new Task(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5),
                        resultSet.getDate(6), resultSet.getString(7));
                        return task;
            }
        }
        return null;
    }

    @Override
    public void update(Task task) throws SQLException {
        String sql = "UPDATE tasks SET name = ?, description = ?, planned_date = ?, dateOfDone = ?, status = ? WHERE " +
                "task_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, task.getPlannedDate());
            preparedStatement.setDate(4,task.getDateOfDone());
            preparedStatement.setString(5, task.getStatus());
            preparedStatement.setInt(6, task.getTaskId());
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(Task task) throws SQLException {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, task.getTaskId());
            preparedStatement.execute();
        }

    }

    @Override
    public List<Task> getAll() throws SQLException {
        String sql = "SELECT * FROM tasks";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            List<Task> tasks = new ArrayList<Task>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Task task = new Task(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getDate(5),  resultSet.getDate(6),
                        resultSet.getString(7));
                tasks.add(task);
            }
            return tasks;
        }
    }

    @Override
    public List<Task> getSortedByCriteria(int journalId, String column, String criteria) throws SQLException {
        String sql = "Select * FROM tasks JOIN journals ON tasks.journal_id = journals.journal_id WHERE journal_id = ? ORDER BY ? ?";
        //String sql = "Select * FROM tasks JOIN journals ON tasks.journal_id = journals.journal_id WHERE (journal_id=?) AND (? = ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, journalId);
            preparedStatement.setString(2, column);
            preparedStatement.setString(3, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> tasks = new ArrayList<Task>();
            while(resultSet.next()){
                Task task = new Task(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5),
                        resultSet.getDate(6), resultSet.getString(7));
                tasks.add(task);
            }
            return tasks;
        }
    }

    @Override
    public List<Task> getFilteredByPattern(int journalId, String column, String pattern, String criteria) throws SQLException {
        //todo ну тоже дописать
        return null;
    }

    @Override
    public List<Task> getFilteredByEquals(int journalId, String column, String equal, String criteria) throws SQLException {
        String sql = "SELECT * FROM tasks JOIN journals ON tasks.journal_id = journals.journal_id WHERE (journal_id = ?) AND (? = ?) ORDER BY ? ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,journalId);
            preparedStatement.setString(2, column);
            preparedStatement.setString(3, equal);
            preparedStatement.setString(4, column);
            preparedStatement.setString(5, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                List<Task> tasks = new ArrayList<Task>();
                Task task = new Task(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5),
                        resultSet.getDate(6), resultSet.getString(7));
            }
        }
        return null;
    }
}
