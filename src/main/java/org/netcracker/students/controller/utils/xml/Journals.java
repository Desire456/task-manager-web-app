package org.netcracker.students.controller.utils.xml;

import org.netcracker.students.model.Journal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement
@XmlSeeAlso({Journal.class})
public class Journals {
    @XmlElement(name = "journal")
    private List<Journal> journals = null;

    public Journals() {}

    public Journals(List<Journal> journals) {
        this.journals = journals;
    }
}
