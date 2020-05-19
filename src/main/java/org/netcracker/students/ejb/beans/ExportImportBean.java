package org.netcracker.students.ejb.beans;

import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.controller.utils.PropertyParser;
import org.netcracker.students.controller.utils.xml.importer.MarshalException;
import org.netcracker.students.controller.utils.xml.importer.UnmarshalException;
import org.netcracker.students.controller.utils.xml.importer.XMLMarshaller;
import org.netcracker.students.ejb.interfaces.IExportImport;
import org.netcracker.students.factories.ExportListFactory;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.exporting.ExportList;
import org.netcracker.students.strategy.exporting.exceptions.ExportException;
import org.netcracker.students.strategy.exporting.exceptions.PrintableExportException;
import org.netcracker.students.strategy.importing.ImportStrategy;
import org.netcracker.students.strategy.importing.ImportStrategyHelper;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ExportImportBean implements IExportImport {
    public String exportObjects(List<Integer> journalIds, List<Integer> tasksIds) throws ExportException, PrintableExportException {
        ExportList exportList = ExportListFactory.getInstance().create(journalIds, tasksIds);

        XMLMarshaller xmlMarshaller = new XMLMarshaller();
        String xml;
        try {
            xml = xmlMarshaller.marshal(exportList);
        } catch (MarshalException e) {
            throw new PrintableExportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        return xml;
    }

    public void importObjects(String xml, int userId)
            throws PrintableImportException, ImportException {
        XMLMarshaller xmlMarshaller = new XMLMarshaller();

        List<Journal> journals = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        try {
            xmlMarshaller.unmarshal(journals, tasks, xml, userId);
        } catch (UnmarshalException e) {
            throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        ImportStrategyHelper importStrategyHelper = ImportStrategyHelper.getInstance();
        PropertyParser propertyParser;
        try {
            propertyParser = PropertyParser.getInstance();
        } catch (PropertyFileException e) {
            throw new PrintableImportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        int strategyId = Integer.parseInt(propertyParser.getProperty(StrategyConstants.JOURNAL_IMPORT_STRATEGY));
        ImportStrategy<Journal> journalImportStrategy =
                importStrategyHelper.resolveJournalStrategy(strategyId);
        for (Journal journal : journals) journalImportStrategy.store(journal);


        strategyId = Integer.parseInt(propertyParser.getProperty(StrategyConstants.TASK_IMPORT_STRATEGY));
        ImportStrategy<Task> taskImportStrategy = importStrategyHelper.resolveTaskStrategy(strategyId);
        for (Task task : tasks) taskImportStrategy.store(task);
    }

}
