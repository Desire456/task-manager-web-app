package org.netcracker.students.dao.postrgresql;

public class DAOErrorConstants {
    public static final String GET_CONNECTION_EXCEPTION_MESSAGE = "Can't get connection ";
    public static final String EXECUTE_SQL_SCRIPT_EXCEPTION_MESSAGE = "Can't execute sql start script ";

    public static final String CREATE_TASK_EXCEPTION_MESSAGE = "Can't create task ";
    public static final String NAME_ALREADY_EXIST_TASK_EXCEPTION_MESSAGE = "task with name %s " +
            "already exist";
    public static final String DELETE_TASK_EXCEPTION_MESSAGE = "Can't delete task ";
    public static final String GET_ALL_TASK_EXCEPTION_MESSAGE = "Can't get all tasks ";
    public static final String GET_FILTERED_BY_EQUALS_TASK_EXCEPTION_MESSAGE = "Can't filter tasks by equals ";
    public static final String GET_FILTERED_BY_PATTERN_TASK_EXCEPTION_MESSAGE = "Can't filter tasks by pattern ";
    public static final String GET_SORTED_BY_CRITERIA_TASK_EXCEPTION_MESSAGE = "Can't sort tasks by this criteria ";
    public static final String READ_TASK_EXCEPTION_MESSAGE = "Can't get task ";
    public static final String UPDATE_TASK_EXCEPTION_MESSAGE = "Can't update task ";
    public static final String TASK_ID_ALREADY_EXIST_EXCEPTION_MESSAGE = "task with this id already exists: id = ";

    public static final String CREATE_USER_EXCEPTION_MESSAGE = "Can't create user ";
    public static final String DELETE_USER_EXCEPTION_MESSAGE = "Can't delete user ";
    public static final String GET_ALL_USER_EXCEPTION_MESSAGE = "Can't get all users ";
    public static final String GET_SORTED_BY_CRITERIA_USER_EXCEPTION_MESSAGE = "Can't sort users by this criteria ";
    public static final String GET_USER_BY_LOGIN_AND_PASSWORD_EXCEPTION_MESSAGE = "Can't get user by login and password ";
    public static final String GET_USER_BY_LOGIN_EXCEPTION_MESSAGE = "Can't get user by login ";
    public static final String READ_USER_EXCEPTION_MESSAGE = "Can't get user ";
    public static final String UPDATE_USER_EXCEPTION_MESSAGE = "Can't update user ";

    public static final String CREATE_JOURNAL_EXCEPTION_MESSAGE = "Can't create journal ";
    public static final String NAME_ALREADY_EXIST_JOURNAL_EXCEPTION_MESSAGE = "journal with name %s " +
            "already exist";
    public static final String DELETE_JOURNAL_EXCEPTION_MESSAGE = "Can't delete journal ";
    public static final String GET_ALL_JOURNAL_BY_USER_ID_EXCEPTION_MESSAGE = "Can't get all journals by user id ";
    public static final String GET_ALL_JOURNAL_EXCEPTION_MESSAGE = "Can't get all journals ";
    public static final String GET_FILTERED_BY_EQUALS_JOURNAL_EXCEPTION_MESSAGE = "Can't filter journals by equals ";
    public static final String GET_FILTERED_BY_PATTERN_JOURNAL_EXCEPTION_MESSAGE = "Can't filter journals by equals ";
    public static final String GET_SORTED_BY_CRITERIA_JOURNAL_EXCEPTION_MESSAGE = "Can't sort journals by this criteria ";
    public static final String READ_JOURNAL_EXCEPTION = "Can't get journal ";
    public static final String UPDATE_JOURNAL_EXCEPTION = "Can't update journal ";

    public static final String JOURNAL_ID_ALREADY_EXIST_JOURNAL_EXCEPTION_MESSAGE = "journal with this id already exist: id = ";
}
