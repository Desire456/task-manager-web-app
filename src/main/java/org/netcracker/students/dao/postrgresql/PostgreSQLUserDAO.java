package org.netcracker.students.dao.postrgresql;

import org.netcracker.students.dao.exceptions.userDAO.*;
import org.netcracker.students.dao.interfaces.UsersDAO;
import org.netcracker.students.factories.UserFactory;
import org.netcracker.students.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLUserDAO implements UsersDAO {
    private Connection connection;

    public PostgreSQLUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User create(String login, String password, String role, Timestamp dateOfRegistration) throws CreateUserException {
        String sql = "INSERT INTO users VALUES (default, ?, ?, ?, ?)";
        String RETURN_USER_SQL = "SELECT * FROM users WHERE login = ?";
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.setTimestamp(4, dateOfRegistration);
            preparedStatement.execute();
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(RETURN_USER_SQL)) {
                preparedStatement1.setString(1, login);
                ResultSet resultSet = preparedStatement1.executeQuery();
                if (resultSet.next()) {
                    user =  UserFactory.createUser(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4),
                            resultSet.getTimestamp(5).toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new CreateUserException(DAOErrorConstants.CREATE_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
        return user;
    }

    @Override
    public User read(int id) throws ReadUserException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = UserFactory.createUser(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getTimestamp(5).toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new ReadUserException(DAOErrorConstants.READ_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) throws UpdateUserException {
        String sql = "UPDATE users SET login = ?, password = ?, role = ? WHERE journal_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new UpdateUserException(DAOErrorConstants.UPDATE_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void delete(int userId) throws DeleteUserException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DeleteUserException(DAOErrorConstants.DELETE_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() throws GetAllUserException {
        String sql = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(UserFactory.createUser(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime()));
            }
            return users;
        } catch (SQLException e) {
            throw new GetAllUserException(DAOErrorConstants.GET_ALL_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public User getByLoginAndPassword(String login, String password) throws GetUserByLoginAndPasswordException {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = UserFactory.createUser(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new GetUserByLoginAndPasswordException(DAOErrorConstants.GET_USER_BY_LOGIN_AND_PASSWORD_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
        return user;
    }

    public User getByLogin(String login) throws GetUserByLoginException {
        String sql = "SELECT * FROM users WHERE login = ?";
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            if (resultSet.next()) {
                user = UserFactory.createUser(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new GetUserByLoginException(DAOErrorConstants.GET_USER_BY_LOGIN_EXCEPTION_MESSAGE + e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> getSortedByCriteria(String column, String criteria) throws GetSortedByCriteriaUser {
        String sql = "SELECT * FROM users ORDER BY ? ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, column);
            preparedStatement.setString(2, criteria);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(UserFactory.createUser(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime()));
            }
            return users;
        } catch (SQLException e) {
            throw new GetSortedByCriteriaUser(DAOErrorConstants.GET_SORTED_BY_CRITERIA_USER_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
