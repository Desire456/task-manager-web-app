package org.netcracker.students.model;


import org.netcracker.students.controller.utils.IdGenerator;
import org.netcracker.students.controller.utils.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Task class, which have a name, description, date of complete, planned date and status
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Task implements Serializable {

    private int id;
    private String name;
    private String description;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime plannedDate;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateOfDone;
    private String status;
    private String formattedPlannedDate;
    private int journalId;


    public Task(int id, String name, String description, LocalDateTime plannedDate, LocalDateTime dateOfDone, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.dateOfDone = dateOfDone;
        this.formattedPlannedDate = this.formatDate(plannedDate);
        this.status = status;
    }

    public Task(Task task) {
        id = task.id;
        name = task.name;
        description = task.description;
        plannedDate = task.plannedDate;
        dateOfDone = task.dateOfDone;
        status = task.status;
    }

    /**
     * Constructor with certain values
     *
     * @param id          - id of task
     * @param name        - name of task
     * @param description - description of task
     * @param plannedDate - planned date of task
     * @param status      - status of task
     */


    public Task(int id, String name, String description, LocalDateTime plannedDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.dateOfDone = null;
        this.status = status;
    }


    /**
     * Default constructor
     */

    public Task() {
        id = IdGenerator.getInstance().getId();
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", plannedDate=" + plannedDate +
                ", dateOfDone=" + dateOfDone +
                ", status=" + status +
                ", id=" + id +
                '}';
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String formatDate(LocalDateTime localDateTime) {
        String dateTimeFormat = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        String formatDateTimeNow = localDateTime.format(formatter);
        return formatDateTimeNow.replace(" ", "T");
    }
}
