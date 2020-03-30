package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.controller.utils.UserIdGenerator;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLUsersDAO implements UsersDAO {
    private Connection connection;

    public PostgreSQLUsersDAO(){
    }


    @Override
    public User create(String login, String password, String role, Date dateOfRegistration) throws SQLException {
        String sql = "INSERT INTO users VALUES (?, ?, ?, ?)";
        String RETURN_USER_SQL = "SELECT * FROM users WHERE login = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.setDate(4, dateOfRegistration);
            preparedStatement.execute();
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(RETURN_USER_SQL)){
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                User user = new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5).toLocalDate());
                return user;
            }
        }
        return null;
    }

    @Override
    public User read(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                User user = new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5).toLocalDate()); //todo скорее всего тут нужна фабрика, как сделали для тасок и использовать подобные фабрики везде в DAO при создании новых экземпляров
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET login = ?, password = ?, role = ? WHERE journal_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, user.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM users";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<User>();
            while(resultSet.next()){
                User user = new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5).toLocalDate());
                users.add(user);
            }
            return users;
        }
    }

    @Override
    public User getByLoginAndPassword(String login, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                User user = new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5).toLocalDate());
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getSortedByCriteria(String column, String criteria) throws SQLException {
        String sql = "SELECT * FROM users ORDER BY ? ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, column);
            preparedStatement.setString(2, criteria); //todo обсудить, будет сюда приходить уже нормальные ASC или DESC (по возрастанию или по убыванию) или же их где-то тут надо преобразовывать из приходящего значения criteria (как и во всех остальных ДАО)
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<User>();
            while(resultSet.next()){
                User user = new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5).toLocalDate());
                users.add(user);
            }
            return users;
        }
    }
}
