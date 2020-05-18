package org.netcracker.students.controller.utils.xml.importer;

import org.netcracker.students.controller.utils.xml.validator.XmlValidator;
import org.netcracker.students.controller.utils.xml.validator.XmlValidatorException;
import org.netcracker.students.factories.JournalFactory;
import org.netcracker.students.factories.TaskFactory;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.exporting.ExportList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                        creationDate.setTextContent(localDateTimeToString(journalList.get(i).getCreationDate()));
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
                    plannedDate.setTextContent(localDateTimeToString(taskList.get(i).getPlannedDate()));
                    task.appendChild(plannedDate);
                    if(taskList.get(i).getDateOfDone()!= null){
                    Element dateOfDone = document.createElement("dateOfDone");
                        dateOfDone.setTextContent(localDateTimeToString(taskList.get(i).getDateOfDone()));
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

    public void unmarshal(List<Journal> journalList, List<Task> taskList, String xml, int userID) throws XMLMarshallerException {
        journalList = new ArrayList<>();
        taskList = new ArrayList<>();
        try{
            XmlValidator.getInstance().checkStringXMLforXSD(xml);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            document.getDocumentElement().normalize();
            NodeList journalsNodeList = document.getElementsByTagName("journals");
            if (journalsNodeList.getLength() !=0){
                NodeList journalNodeList = document.getElementsByTagName("journal");
                for(int i = 0; i < journalNodeList.getLength(); i++){
                    journalList.add(getJournal(journalNodeList.item(i), userID));
                }
            }
            NodeList tasksNodeList = document.getElementsByTagName("tasks");
            if(tasksNodeList.getLength() != 0){
                NodeList taskNodeList = document.getElementsByTagName("task");
                    for (int i = 0; i < taskNodeList.getLength(); i++){
                        taskList.add(getTask(taskNodeList.item(i)));
                    }
            }
        }
        catch (XmlValidatorException e) {
            throw new XMLMarshallerException("XML validate error: "+e.getMessage());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new XMLMarshallerException("XML parse error: "+e.getMessage());
        }
    }

    private Journal getJournal(Node node, int userID){
        Journal journal = null;
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) node;
            journal = JournalFactory.createJournal(Integer.parseInt(getTagValue("id", element)),getTagValue("name", element),
                    getTagValue("description", element), userID, stringToLocalDateTime(getTagValue("creationDate", element)),
                    true);
        }
        return journal;
    }

    private Task getTask(Node node){
        Task task = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            task = TaskFactory.createTask(Integer.parseInt(getTagValue("id", element)),Integer.parseInt(getTagValue("journalId", element)),
                    getTagValue("name", element),getTagValue("description", element),stringToLocalDateTime(getTagValue("plannedDate", element)),
                    stringToLocalDateTime(getTagValue("dateOfDone", element)),getTagValue("status", element));
        }
        return task;
    }

    private String getTagValue(String tag, Element element){
        String nodeValue = null;
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node1 = nodeList.item(0);
        if (node1 != null)
        {
            NodeList nodeList1 = node1.getChildNodes();
            Node node = (Node) nodeList1.item(0);
            nodeValue = node.getNodeValue();
        }
        return nodeValue;
    }

    private String localDateTimeToString(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return localDateTime.format(formatter);
    }

    private LocalDateTime stringToLocalDateTime(String str){
        LocalDateTime localDateTime = null;
        if(str != null){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            localDateTime = LocalDateTime.parse(str, formatter);
        }
        return localDateTime;
    }

}
