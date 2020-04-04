package org.netcracker.students.model;


import org.netcracker.students.controller.utils.xml.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Main shared.model class, which stores tasks
 *
 * @see Task
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Journal implements Serializable {

    private Map<Integer, Task> tasks;
    private int id;
    private int userId;
    private String accessModifier;
    private String name;
    @XmlJavaTypeAdapter(LocalDateAdapter.class) //todo изменить на LocalDateTime
    private LocalDateTime creationDate;
    private String description;

    public Journal(int id, String name, String description, int userId,
                   LocalDateTime creationDate, String accessModifier) {
        tasks = new HashMap<>();
        this.id = id;
        this.accessModifier = accessModifier;
        this.userId = userId;
        this.name = name;
        this.creationDate = creationDate;
        this.description = description;
    }

    public Journal(String name, String description, int userId, LocalDateTime creationDate, String accessModifier) {
        tasks = new HashMap<>();
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.creationDate = creationDate;
        this.accessModifier = accessModifier;
    }


    public Journal() {
        tasks = new HashMap<Integer, Task>();
    }

    public Journal(Journal journal) {
        tasks = new HashMap<>();
        this.id = journal.id;
        this.accessModifier = journal.accessModifier;
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

    public void addTask(Task task) {
        /*while (tasks.containsKey(task.getId())) {
            task.setId(IdGenerator.getInstance().getId());
        }*/
        tasks.put(task.getId(), task);
    }

    /**
     * Delete task from map by id
     *
     * @param id - desired id
     */

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    /**
     * Getter function by id
     *
     * @param id - desired id
     */


    public Task getTask(int id) {
        return tasks.get(id);
    }

    /**
     * Getter function by name
     *
     * @param name - desired name
     */

    public Task getTaskByName(String name) {
        Task res = null;
        for (int i = 0; i < tasks.size(); ++i) {
            if (name.equals(tasks.get(i).getName())) res = tasks.get(i);
        }
        return res;
    }

    /**
     * Getter function by date
     *
     * @param date - desired date
     * @return desired task
     */

    public Task getTaskByDate(LocalDateTime date) {
        Task res = null;
        for (int i = 0; i < tasks.size(); ++i) {
            if (date == tasks.get(i).getPlannedDate()) res = tasks.get(i);
        }
        return res;
    }

    /**
     * Change function by id
     *
     * @param id   - desired id
     * @param task - new task
     */

    public void changeTask(int id, Task task) {
        Task res = tasks.get(id);

        res.setPlannedDate(task.getPlannedDate());
        res.setDateOfDone(task.getDateOfDone());
        res.setName(task.getName());
        res.setDescription(task.getDescription());
        res.setStatus(task.getStatus());
    }


    public void changeStatus(String taskName, String status) {
        getTaskByName(taskName).setStatus(status);
    }

    /**
     * @return unmodifiable list of all tasks
     */

    public ArrayList<Task> getAll() {
        Task[] arr = tasks.values().toArray(new Task[0]);
        return new ArrayList<>(Arrays.asList(arr));
    }

    public boolean isTaskInJournal(Task task) {
        boolean res = false;
        List<Task> taskList = this.getAll();
        for (Task curTask : taskList) {
            res = (task.getPlannedDate().isEqual(curTask.getPlannedDate())) && (task.getStatus() == curTask.getStatus()) &&
                    (task.getName().equals(curTask.getName())) && (task.getDateOfDone() == curTask.getDateOfDone()) &&
                    (task.getDescription().equals(curTask.getDescription()));
            if (res) return true;
        }
        return false;
    }

    public boolean isTaskInJournal(int id) {
        return tasks.containsKey(id);
    }

    public int size() {
        return tasks.size();
    }
}