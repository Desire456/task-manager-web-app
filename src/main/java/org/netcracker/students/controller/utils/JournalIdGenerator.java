package org.netcracker.students.controller.utils;

public class JournalIdGenerator {
    private static JournalIdGenerator instance;
    private static int id;

    private JournalIdGenerator(){

    }

    public static JournalIdGenerator getInstance(){
        if (instance==null)
            instance = new JournalIdGenerator();
        return instance;
    }

    public static int getId(){
        return id++;
    }
}
