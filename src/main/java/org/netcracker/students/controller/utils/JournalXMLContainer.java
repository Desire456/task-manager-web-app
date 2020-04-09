package org.netcracker.students.controller.utils;

import org.netcracker.students.dto.JournalDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement
@XmlSeeAlso({JournalDTO.class})
public class JournalXMLContainer {
    @XmlElement(name = "journal")
    private List<JournalDTO> journals;

    public JournalXMLContainer() {
    }

    public JournalXMLContainer(List<JournalDTO> journals) {
        this.journals = journals;
    }
}