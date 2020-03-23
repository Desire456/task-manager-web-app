package org.netcracker.students.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Journal {
    private int journalId;
    private int userId;
    private String name;
    private String description;
    private Date creationDate; //date
    private boolean isPrivate;

    public Journal(){}

    public Journal(int journalId, String name, String description, boolean isPrivate, Date creationDate,
                   int user){
        this.journalId = journalId;
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
        this.creationDate = creationDate;
        this.userId = user;
    }

    //region Getters
    public int getJournalId() {
        return journalId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getUserId() {
        return userId;
    }

    public boolean getPrivateFlag() { return isPrivate;}

    //endregion

    //region Setters
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setJournalId(int journalId) {
        this.journalId = journalId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //endregion
}
