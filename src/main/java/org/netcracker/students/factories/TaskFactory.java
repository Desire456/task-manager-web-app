package org.netcracker.students.factories;


import org.netcracker.students.model.Task;

import java.time.LocalDateTime;

/**
 * Factory pattern class creating tasks
 *
 * @see Task
 */

public class TaskFactory {
    public static Task createTask(String name, String description, LocalDateTime plannedDate, String status, int journalId) {
        return new Task(name, description, plannedDate, status, journalId);
    }

    public static Task createTask(int id, int journalId, String name, String description, LocalDateTime plannedDate,
                           LocalDateTime dateOfDone, String status) {
        return new Task(id, journalId, name, description, plannedDate, dateOfDone, status);
    }

    /**
     * Copy constructor
     *
     * @param task - copy object
     * @return copy of argument
     */

    public static Task createTask(Task task) {
        return new Task(task);
    }
}
