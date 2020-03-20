package org.netcracker.students.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlRootElement
@XmlSeeAlso({Journal.class})
public class Journals extends ArrayList<Journal> {
    public Journals() {
    }

    public Journals(ArrayList<Journal> journalArrayList) {
        this.addAll(journalArrayList);
    }

    @XmlElement(name = "journal")
    public ArrayList<Journal> getJournals() {
        return this;
    }
}
