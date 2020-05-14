package org.netcracker.students.factories;

import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.exporting.*;

import java.util.List;

public class ExportListFactory {
    private static ExportListFactory instance;

    public static ExportListFactory getInstance() {
        if(instance == null) {
            instance = new ExportListFactory();
        }
        return instance;
    }

    private ExportListFactory() {
    }

    public ExportList create(List<Integer> journalIds, List<Integer> taskIds) throws ReadTaskException,
            GetConnectionException, GetAllTaskException, ReadJournalException, PropertyFileException {
        ExportList exportList = new ExportList();

        this.fillExportByType(exportList, StrategyConstants.JOURNAL_TYPE, journalIds);
        this.fillExportByType(exportList, StrategyConstants.TASK_TYPE, taskIds);

        return  exportList;
    }

    private void fillExportByType(ExportList exportList, String type, List<Integer> ids) throws GetAllTaskException,
            ReadTaskException, ReadJournalException, GetConnectionException, PropertyFileException {
        ExportConfigItem configItem = ExportConfig.getInstance().get(type);
        ExportStrategy exportStrategy = ExportStrategyHelper.getInstance().resolveStrategy(configItem);
        exportStrategy.collectExportIds(exportList, ids);
    }


}
