package org.netcracker.students.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Journal is main entity we'll be using to store tasks
 * @see Task
 */
@Entity
@Table(name = "journals")
public class Journal implements Serializable {

    @Id
    @Column(name = "journal_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    /**
     * Id of user, which store this journal
     */
    @Column(name = "user_id")
    private int userId;

    /**
     * Private flag. Journals can be private and public
     */
    @Column(name = "is_private")
    private boolean isPrivate;

    /**
     * Name of journal
     */
    @Column(name = "name")
    private String name;

    /**
     * Date when this journal was created
     */
    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    /**
     * Description of journal
     */
    @Column(name = "description")
    private String description;

    public Journal(int id, String name, String description, int userId, LocalDateTime creationDate, boolean isPrivate) {

        this.id = id;
        this.isPrivate = isPrivate;
        this.userId = userId;
        this.name = name;
        this.creationDate = creationDate;
        this.description = description;
    }

    public Journal(String name, String description, int userId, LocalDateTime creationDate, boolean isPrivate) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.creationDate = creationDate;
        this.isPrivate = isPrivate;
    }


    public Journal() {
    }

    public Journal(Journal journal) {
        this.id = journal.id;
        this.isPrivate = journal.isPrivate;
        this.name = journal.name;
        this.creationDate = journal.creationDate;
        this.description = journal.description;
        this.userId = journal.userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}