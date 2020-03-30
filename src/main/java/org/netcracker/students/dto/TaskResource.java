package org.netcracker.students.dto;

import java.util.List;

public class TaskResource {
    List<TaskTO> tasks;

    public TaskResource(List<TaskTO> tasks) {
        this.tasks = tasks;
    }

    public List<TaskTO> getAllTasks() {
        return tasks;
    }

    public void save(TaskTO task) {
        tasks.add(task);
    }

    public void delete(String taskId) {
        tasks.removeIf(task -> task.getId().equals(taskId));
    }

}
