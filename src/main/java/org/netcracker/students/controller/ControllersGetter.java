package org.netcracker.students.controller;



import java.util.HashMap;

public class ControllersGetter {
    private static ControllersGetter instance;

    public static synchronized ControllersGetter getInstance() {
        if (instance == null) {
            instance = new ControllersGetter();
        }
        return instance;
    }

    private HashMap<Integer, TasksController> controllers;

    private ControllersGetter() {
        this.controllers = new HashMap<>();
    }

    void addController(TasksController tasksController) {
        this.controllers.put(tasksController.getId(), tasksController);
    }

    public TasksController getController(int id) {
        return this.controllers.get(id);
    }

    void removeController(int id) {
        this.controllers.remove(id);
    }
}