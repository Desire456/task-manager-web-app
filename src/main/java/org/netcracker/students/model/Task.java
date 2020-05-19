package org.netcracker.students.model;


import org.netcracker.students.controller.utils.LocalDateTimeAdapter;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Task class, which have a name, description, date of complete, planned date and status
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @Column(name = "task_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "planned_date")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime plannedDate;

    @Column(name = "date_of_done")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateOfDone;

    @Column(name = "status")
    private String status;

    @Column(name = "journal_id")
    private int journalId;


    public Task(int id, int journalId, String name, String description, LocalDateTime plannedDate,
                LocalDateTime dateOfDone, String status) throws GetConnectionException, ReadJournalException {
        this.id = id;
        this.journalId = journalId;
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.dateOfDone = dateOfDone;
        this.status = status;
    }

    public Task(Task task) {
        id = task.id;
        journalId = task.journalId;
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
    }

    public Task(String name, String description, LocalDateTime plannedDate, String status, int journalId)
            throws GetConnectionException, ReadJournalException {
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

    public int getJournalId() {
        return journalId;
    }

    public void setJournalId(int journalId) {
        this.journalId = journalId;
    }
}