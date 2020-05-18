package org.netcracker.students.controller.utils.xml.importer;

import org.netcracker.students.controller.utils.xml.validator.XmlValidator;
import org.netcracker.students.controller.utils.xml.validator.XmlValidatorException;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.exporting.ExportList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class XMLMarshaller {
    private static XMLMarshaller instance;

    private XMLMarshaller() {}

    public static XMLMarshaller getInstance() {
        if (instance == null) instance = new XMLMarshaller();
        return instance;
    }

    public String marshal(ExportList exportList) throws XMLMarshallerException {
        String outputString = null;
        List<Journal> journalList = exportList.getJournals();
        List<Task> taskList = exportList.getTasks();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element importData = document.createElement("importData");
            document.appendChild(importData);
            if (journalList.size()!=0){
                Element journals = document.createElement("journals");
                importData.appendChild(journals);
                for (int i = 0; i < journalList.size(); i++){
                        Element journal = document.createElement("journal");
                        Element id = document.createElement("id");
                        id.setTextContent(String.valueOf(journalList.get(i).getId()));
                        journal.appendChild(id);
                        Element name = document.createElement("name");
                        name.setTextContent(journalList.get(i).getName());
                        journal.appendChild(name);
                        Element description = document.createElement("description");
                        description.setTextContent(journalList.get(i).getDescription());
                        journal.appendChild(description);
                        Element creationDate = document.createElement("creationDate");
                        creationDate.setTextContent(formatLocalDateTime(journalList.get(i).getCreationDate()));
                        journal.appendChild(creationDate);
                        Element isPrivate = document.createElement("isPrivate");
                        isPrivate.setTextContent(String.valueOf(journalList.get(i).getIsPrivate()));
                        journal.appendChild(isPrivate);
                        journals.appendChild(journal);
                }
            }
            if (taskList.size() != 0) {
                Element tasks = document.createElement("tasks");
                importData.appendChild(tasks);
                for (int i = 0; i < taskList.size(); i++){
                    Element task = document.createElement("task");
                    Element id = document.createElement("id");
                    id.setTextContent(String.valueOf(taskList.get(i).getId()));
                    task.appendChild(id);
                    Element name = document.createElement("name");
                    name.setTextContent(taskList.get(i).getName());
                    task.appendChild(name);
                    Element description = document.createElement("description");
                    description.setTextContent(taskList.get(i).getDescription());
                    task.appendChild(description);
                    Element status = document.createElement("status");
                    status.setTextContent(taskList.get(i).getStatus());
                    task.appendChild(status);
                    Element plannedDate = document.createElement("plannedDate");
                    plannedDate.setTextContent(formatLocalDateTime(taskList.get(i).getPlannedDate()));
                    task.appendChild(plannedDate);
                    if(taskList.get(i).getDateOfDone()!= null){
                    Element dateOfDone = document.createElement("dateOfDone");
                        dateOfDone.setTextContent(formatLocalDateTime(taskList.get(i).getDateOfDone()));
                        task.appendChild(dateOfDone);
                    }
                    Element journalId = document.createElement("journalId");
                    journalId.setTextContent(String.valueOf(taskList.get(i).getJournalId()));
                    task.appendChild(journalId);
                    tasks.appendChild(task);
                }
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            //StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            if (XmlValidator.getInstance().checkStringXMLforXSD(writer.toString())) outputString = writer.toString();
        } catch (TransformerException | ParserConfigurationException e) {
            throw new XMLMarshallerException("Creation XML error: "+e.getMessage());
        } catch (XmlValidatorException e) {
            throw new XMLMarshallerException("XML validate error: "+e.getMessage());
        }
        return outputString;
    }

    public void unmarshal(){}

    private String formatLocalDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return localDateTime.format(formatter);
    }

}
