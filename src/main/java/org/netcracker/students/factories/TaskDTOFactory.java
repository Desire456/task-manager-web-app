package org.netcracker.students.factories;

import org.netcracker.students.model.dto.TaskDTO;

import java.time.LocalDateTime;

public class TaskDTOFactory {
    public static TaskDTO createTaskDTO(int id, String name, String description, String status, LocalDateTime plannedDate,
                                        LocalDateTime dateOfDone) {
        return new TaskDTO(id, name, description, status, plannedDate, dateOfDone);
    }
}