package org.netcracker.students.controller.utils;

public class UserIdGenerator {
    private static UserIdGenerator instance;
    private static int id = 0;

    private UserIdGenerator(){

    }

    public static synchronized  UserIdGenerator getInstance(){
        if (instance == null)
            instance = new UserIdGenerator();
        return instance;
    }

    public static int getId() {
        return id++;
    }
}
