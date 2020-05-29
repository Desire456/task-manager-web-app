package org.netcracker.students.controller.utils;

import org.netcracker.students.model.Task;
import org.netcracker.students.model.dto.TaskDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement(name = XMLConstants.XML_ROOT_NAME_TASKS)
@XmlSeeAlso({Task.class})
public class TaskXMLContainer {
    @XmlElement(name = XMLConstants.XML_ELEMENT_NAME_TASK)
    private List<TaskDTO> tasks = null;

    public TaskXMLContainer() {
    }

    public TaskXMLContainer(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

}