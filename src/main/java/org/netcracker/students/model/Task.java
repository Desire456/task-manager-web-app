package org.netcracker.students.model;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Task entity stores in journal
 * @see Journal
 */
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    /**
     * Name of task
     */
    @Column(name = "name")
    private String name;

    /**
     * Description of task
     */
    @Column(name = "description")
    private String description;

    /**
     * The date on which this task is scheduled to be completed
     */
    @Column(name = "planned_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime plannedDate;

    /**
     * The date when the task was completed
     */
    @Column(name = "date_of_done", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateOfDone;

    /**
     *  Task status to monitor productivity
     */
    @Column(name = "status")
    private String status;

    /**
     * Id of journal which store this task
     */
    @Column(name = "journal_id")
    private int journalId;


    public Task(int id, int journalId, String name, String description, LocalDateTime plannedDate,
                LocalDateTime dateOfDone, String status) {
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

    public Task(String name, String description, LocalDateTime plannedDate, String status, int journalId) {
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