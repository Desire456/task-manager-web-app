package org.netcracker.students.controller.utils;

import org.netcracker.students.model.Journal;
import org.netcracker.students.model.dto.JournalDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement(name = XMLConstants.XML_ROOT_NAME_JOURNALS)
@XmlSeeAlso({Journal.class})
public class JournalXMLContainer {
    @XmlElement(name = XMLConstants.XML_ELEMENT_NAME_JOURNAL)
    private List<JournalDTO> journals = null;

    public JournalXMLContainer() {
    }

    public JournalXMLContainer(List<JournalDTO> journals) {
        this.journals = journals;
    }
}