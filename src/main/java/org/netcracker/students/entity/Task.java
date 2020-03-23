package org.netcracker.students.entity;

import java.sql.Date;

public class Task {
    private int taskId;
    private int journalId;
    private String name;
    private String description;
    private String status;
    private Date plannedDate;
    private Date dateOfDone;


    public Task(){}

    public Task(int taskId, int journalId,  String name, String description, Date plannedDate, Date dateOfDone,
                String status){
        this.taskId = taskId;
        this.journalId = journalId;
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.dateOfDone = dateOfDone;
        this.status = status;
    }

    //region Getters
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getJournalId() {
        return journalId;
    }

    public String getStatus() {
        return status;
    }

    public Date getDateOfDone() {
        return dateOfDone;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }
    //endregion

    //region Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJournalId(int journalId) {
        this.journalId = journalId;
    }

    public void setDateOfDone(Date dateOfDone) {
        this.dateOfDone = dateOfDone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    //endregion
}
