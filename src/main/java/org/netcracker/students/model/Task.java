package org.netcracker.students.model;


import org.netcracker.students.controller.utils.xml.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
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
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate plannedDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfDone;
    private String status;
    private String formattedPlannedDate;
    private int journalId;


    public Task(int id, int journalId, String name, String description, LocalDate plannedDate, LocalDate dateOfDone, String status) {
        this.id = id;
        this.journalId = journalId;
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.dateOfDone = dateOfDone;
        this.formattedPlannedDate = this.formatDate(plannedDate);
        this.status = status;
    }

    public Task(Task task) {
        id = task.id;
        journalId = task.journalId;
        name = task.name;
        description = task.description;
        plannedDate = task.plannedDate;
        formattedPlannedDate = task.formattedPlannedDate;
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


    public Task(int id, String name, String description, LocalDate plannedDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.formattedPlannedDate = this.formatDate(plannedDate);
        this.dateOfDone = null;
        this.status = status;
    }


    /**
     * Default constructor
     */

    public Task() {
       // id = IdGenerator.getInstance().getId();
    }

    public Task(String name, String description, LocalDate plannedDate, String status, int journalId) {
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.status = status;
        this.journalId = journalId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJournalId() {
        return journalId;
    }

    public void setJournalId(int journalId) {
        this.journalId = journalId;
    }

    private String formatDate(LocalDate localDate) {
        String dateTimeFormat = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return localDate.format(formatter);
    }
}
