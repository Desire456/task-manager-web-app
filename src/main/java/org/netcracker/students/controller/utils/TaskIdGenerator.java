package org.netcracker.students.controller.utils;

public class TaskIdGenerator {
    private static TaskIdGenerator instance;
    private static int id;

    private TaskIdGenerator(){

    }

    public static TaskIdGenerator getInstance(){
        if(instance == null)
            instance = new TaskIdGenerator();
        return instance;
    }

    public static int getId(){
        return id++;
    }
}
