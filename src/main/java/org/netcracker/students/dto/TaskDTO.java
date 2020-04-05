package org.netcracker.students.dto;

import org.netcracker.students.controller.utils.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TaskDTO {
    private int id;
    private String name;
    private String description;
    private String status;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime plannedDate;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateOfDone;
    private String formattedPlannedDate;

    public TaskDTO() {}

    public TaskDTO(int id, String name, String description, String status, LocalDateTime plannedDate, LocalDateTime dateOfDone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.plannedDate = plannedDate;
        this.dateOfDone = dateOfDone;
        this.formattedPlannedDate = this.formatDateTime(plannedDate);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public LocalDateTime getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDateTime plannedDate) {
        this.plannedDate = plannedDate;
    }

    public LocalDateTime getDateOfDone() {
        return dateOfDone;
    }

    public void setDateOfDone(LocalDateTime dateOfDone) {
        this.dateOfDone = dateOfDone;
    }

    public String getFormattedPlannedDate() {
        return formattedPlannedDate;
    }

    public void setFormattedPlannedDate(String formattedPlannedDate) {
        this.formattedPlannedDate = formattedPlannedDate;
    }

    private String formatDateTime(LocalDateTime localDateTime) {
        String dateTimeFormat = "yyyy-MM-dd'T'HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return localDateTime.format(formatter);
    }
}
