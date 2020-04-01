package org.netcracker.students.dto;

import java.time.LocalDate;

public class TaskDTO {
    private String id;
    private String name;
    private String description;
    private String status;
    private LocalDate plannedDate;
    private LocalDate dateOfDone;

    public TaskDTO(String id, String name, String description, String status, LocalDate plannedDate, LocalDate dateOfDone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.plannedDate = plannedDate;
        this.dateOfDone = dateOfDone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }

    public LocalDate getDateOfDone() {
        return dateOfDone;
    }

    public void setDateOfDone(LocalDate dateOfDone) {
        this.dateOfDone = dateOfDone;
    }
}
