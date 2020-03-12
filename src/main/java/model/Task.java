
package model;

import utils.IdGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Task class, which have a name, description, date of complete, planned date and status
 */


public class Task implements Serializable {
    private int id;
    private int journalId;
    private String name;
    private String description;
    private LocalDateTime plannedDate;
    private LocalDateTime dateOfDone;
    private String status;

    public Task(int id, int journalId, String name, String description, LocalDateTime plannedDate, LocalDateTime dateOfDone, String status) {
        this.id = id;
        this.journalId = journalId;
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.dateOfDone = dateOfDone;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
