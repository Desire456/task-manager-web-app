package org.netcracker.students.factories;

import org.netcracker.students.dto.TaskDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskDTOFactory {
    public TaskDTO createTaskDTO(String id, String name, String description, String status, LocalDate plannedDate, LocalDate dateOfDone) {
        return new TaskDTO(id, name, description, status, plannedDate, dateOfDone);
    }
}
