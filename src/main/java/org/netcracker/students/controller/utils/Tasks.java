package org.netcracker.students.controller.utils;

import org.netcracker.students.model.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement
@XmlSeeAlso({Task.class})
public class Tasks {
    @XmlElement(name = "task")
    private List<Task> tasks = null;

    public Tasks() {}

    public Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
