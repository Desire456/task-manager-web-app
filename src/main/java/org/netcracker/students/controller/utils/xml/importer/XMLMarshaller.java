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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class XMLMarshaller {

    public String marshal(ExportList exportList) throws MarshalException {
        String outputString = null;
        List<Journal> journalList = exportList.getJournals();
        List<Task> taskList = exportList.getTasks();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element importData = document.createElement(XMLMarshallerConstants.XML_ROOT_ELEMENT_TAG_NAME);
            document.appendChild(importData);
            if (journalList.size() != 0) {
                Element journals = document.createElement(XMLMarshallerConstants.XML_JOURNALS_ELEMENT_TAG_NAME);
                importData.appendChild(journals);
                createJournalElementXml(journalList, document, journals);
            }
            if (taskList.size() != 0) {
                Element tasks = document.createElement(XMLMarshallerConstants.XML_TASKS_ELEMENT_TAG_NAME);
                importData.appendChild(tasks);
                createTaskElementXml(taskList, document, tasks);
            }
            StringWriter writer = writeDocument(document);
            if (XmlValidator.getInstance().checkStringXMLforXSD(writer.toString())) outputString = writer.toString();
        } catch (ParserConfigurationException | WriteDocumentException e) {
            throw new MarshalException(XMLMarshallerConstants.XML_CREATION_EXCEPTION_MESSAGE + e.getMessage());
        } catch (XmlValidatorException e) {
            throw new MarshalException(XMLMarshallerConstants.VALIDATE_EXCEPTION_MESSAGE + e.getMessage());
        }
        return outputString;
    }

    private StringWriter writeDocument(Document document) throws WriteDocumentException {
        StringWriter writer = new StringWriter();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new WriteDocumentException(XMLMarshallerConstants.XML_WRITE_EXCEPTION_MESSAGE + e.getMessage());
        }
        return writer;
    }

    private void createJournalElementXml(List<Journal> journalList, Document document, Element journals) {
        for (int i = 0; i < journalList.size(); i++) {
            Element journal = document.createElement(XMLMarshallerConstants.XML_JOURNAL_ELEMENT_TAG_NAME);
            journal.appendChild(setTextContent(XMLMarshallerConstants.XML_ID_ELEMENT_TAG_NAME,
                    document, String.valueOf(journalList.get(i).getId())));
            journal.appendChild(setTextContent(XMLMarshallerConstants.XML_NAME_ELEMENT_TAG_NAME,
                    document, journalList.get(i).getName()));
            journal.appendChild(setTextContent(XMLMarshallerConstants.XML_DESCRIPTION_ELEMENT_TAG_NAME,
                    document, journalList.get(i).getDescription()));
            journal.appendChild(setTextContent(XMLMarshallerConstants.XML_CREATION_DATE_ELEMENT_TAG_NAME,
                    document, localDateTimeToString(journalList.get(i).getCreationDate())));
            journal.appendChild(setTextContent(XMLMarshallerConstants.XML_IS_PRIVATE_ELEMENT_TAG_NAME,
                    document, String.valueOf(journalList.get(i).getIsPrivate())));
            journals.appendChild(journal);
        }
    }

    private void createTaskElementXml(List<Task> taskList, Document document, Element tasks) {
        for (int i = 0; i < taskList.size(); i++) {
            Element task = document.createElement(XMLMarshallerConstants.XML_TASK_ELEMENT_TAG_NAME);
            task.appendChild(setTextContent(XMLMarshallerConstants.XML_ID_ELEMENT_TAG_NAME, document,
                    String.valueOf(taskList.get(i).getId())));
            task.appendChild(setTextContent(XMLMarshallerConstants.XML_NAME_ELEMENT_TAG_NAME, document,
                    taskList.get(i).getName()));
            task.appendChild(setTextContent(XMLMarshallerConstants.XML_DESCRIPTION_ELEMENT_TAG_NAME, document,
                    taskList.get(i).getDescription()));
            task.appendChild(setTextContent(XMLMarshallerConstants.XML_STATUS_ELEMENT_TAG_NAME, document,
                    taskList.get(i).getStatus()));
            task.appendChild(setTextContent(XMLMarshallerConstants.XML_PLANNED_DATE_ELEMENT_TAG_NAME, document,
                    localDateTimeToString(taskList.get(i).getPlannedDate())));
            if (taskList.get(i).getDateOfDone() != null)
                task.appendChild(setTextContent(XMLMarshallerConstants.XML_DATE_OF_DONE_ELEMENT_TAG_NAME, document,
                        localDateTimeToString(taskList.get(i).getDateOfDone())));
            task.appendChild(setTextContent(XMLMarshallerConstants.XML_JOURNAL_ID_ELEMENT_TAG_NAME, document,
                    String.valueOf(taskList.get(i).getJournalId())));
            tasks.appendChild(task);
        }
    }

    private Element setTextContent(String tagName, Document document, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        return element;
    }

    public void unmarshal(List<Journal> journalList, List<Task> taskList, String xml, int userID) throws UnmarshalException {
        try {
            XmlValidator.getInstance().checkStringXMLforXSD(xml);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            document.getDocumentElement().normalize();
            NodeList journalsNodeList = document.getElementsByTagName(XMLMarshallerConstants.XML_JOURNALS_ELEMENT_TAG_NAME);
            if (journalsNodeList.getLength() != 0) {
                NodeList journalNodeList = document.getElementsByTagName(XMLMarshallerConstants.XML_JOURNAL_ELEMENT_TAG_NAME);
                for (int i = 0; i < journalNodeList.getLength(); i++) {
                    journalList.add(getJournal(journalNodeList.item(i), userID));
                }
            }
            NodeList tasksNodeList = document.getElementsByTagName(XMLMarshallerConstants.XML_TASKS_ELEMENT_TAG_NAME);
            if (tasksNodeList.getLength() != 0) {
                NodeList taskNodeList = document.getElementsByTagName(XMLMarshallerConstants.XML_TASK_ELEMENT_TAG_NAME);
                for (int i = 0; i < taskNodeList.getLength(); i++) {
                    taskList.add(getTask(taskNodeList.item(i)));
                }
            }
        } catch (XmlValidatorException | XMLDataParseException e) {
            throw new UnmarshalException(XMLMarshallerConstants.VALIDATE_EXCEPTION_MESSAGE + e.getMessage());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new UnmarshalException(XMLMarshallerConstants.XML_PARSE_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    private Journal getJournal(Node node, int userID) throws XMLDataParseException {
        Journal journal = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            journal = JournalFactory.createJournal(Integer.parseInt(getTagValue(XMLMarshallerConstants.XML_ID_ELEMENT_TAG_NAME,
                    element)), getTagValue(XMLMarshallerConstants.XML_NAME_ELEMENT_TAG_NAME, element),
                    getTagValue(XMLMarshallerConstants.XML_DESCRIPTION_ELEMENT_TAG_NAME, element), userID,
                    stringToLocalDateTime(getTagValue(XMLMarshallerConstants.XML_CREATION_DATE_ELEMENT_TAG_NAME, element)),
                    true);
        }
        return journal;
    }

    private Task getTask(Node node) throws XMLDataParseException {
        Task task = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            task = TaskFactory.createTask(Integer.parseInt(getTagValue(XMLMarshallerConstants.XML_ID_ELEMENT_TAG_NAME, element)),
                    Integer.parseInt(getTagValue(XMLMarshallerConstants.XML_JOURNAL_ID_ELEMENT_TAG_NAME, element)),
                    getTagValue(XMLMarshallerConstants.XML_NAME_ELEMENT_TAG_NAME, element),
                    getTagValue(XMLMarshallerConstants.XML_DESCRIPTION_ELEMENT_TAG_NAME, element),
                    stringToLocalDateTime(getTagValue(XMLMarshallerConstants.XML_PLANNED_DATE_ELEMENT_TAG_NAME, element)),
                    stringToLocalDateTime(getTagValue(XMLMarshallerConstants.XML_DATE_OF_DONE_ELEMENT_TAG_NAME, element)),
                    getTagValue(XMLMarshallerConstants.XML_STATUS_ELEMENT_TAG_NAME, element));
        }
        return task;
    }

    private String getTagValue(String tag, Element element) {
        String nodeValue = null;
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node1 = nodeList.item(0);
        if (node1 != null) {
            NodeList nodeList1 = node1.getChildNodes();
            Node node = (Node) nodeList1.item(0);
            nodeValue = node.getNodeValue();
        }
        return nodeValue;
    }

    private String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return localDateTime.format(formatter);
    }

    private LocalDateTime stringToLocalDateTime(String str) throws XMLDataParseException {
        LocalDateTime localDateTime = null;
        try{
            if (str != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                localDateTime = LocalDateTime.parse(str, formatter);
            }
        }
        catch (DateTimeParseException e){
            throw new XMLDataParseException(str+" is not a date");
        }
        return localDateTime;
    }

}
