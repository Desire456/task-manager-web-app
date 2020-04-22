package org.netcracker.students.controller.utils;

import org.netcracker.students.model.dto.TaskDTO;
import org.netcracker.students.model.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlSeeAlso({Task.class})
public class TaskXMLContainer {
    @XmlElement(name = "task")
    private List<TaskDTO> tasks = null;

    public TaskXMLContainer() {
    }

    public TaskXMLContainer(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

}