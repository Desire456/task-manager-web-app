package org.netcracker.students.controller.utils;

import org.netcracker.students.dto.TaskDTO;
import org.netcracker.students.model.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement
@XmlSeeAlso({Task.class})
public class TaskXMLContainer {
    @XmlElement(name = "task")
    private List<TaskDTO> tasks;

    public TaskXMLContainer() {
    }

    public TaskXMLContainer(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

}