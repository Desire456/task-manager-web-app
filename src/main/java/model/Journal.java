package model;

import java.time.LocalDateTime;

public class Journal {
    private int id;
    private int userId;
    private String accessModifier;
    private String name;
    private LocalDateTime creationDate;
    private String description;

    public Journal(int id, String name, String accessModifier, LocalDateTime creationDate, String description) {
        this.id = id;
        this.accessModifier = accessModifier;
        this.name = name;
        this.creationDate = creationDate;
        this.description = description;
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

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
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
