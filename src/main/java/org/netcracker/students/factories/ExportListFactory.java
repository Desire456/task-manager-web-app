package org.netcracker.students.factories;

import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.StrategyNotFoundException;
import org.netcracker.students.strategy.exporting.ExportList;
import org.netcracker.students.strategy.exporting.ExportStrategy;
import org.netcracker.students.strategy.exporting.ExportStrategyHelper;
import org.netcracker.students.strategy.exporting.config.ExportConfig;
import org.netcracker.students.strategy.exporting.config.ExportConfigItem;
import org.netcracker.students.strategy.exporting.exceptions.ExportException;
import org.netcracker.students.strategy.exporting.exceptions.PrintableExportException;

import java.util.List;

public class ExportListFactory {
    private static ExportListFactory instance;

    public static ExportListFactory getInstance() {
        if (instance == null) {
            instance = new ExportListFactory();
        }
        return instance;
    }

    private ExportListFactory() {
    }

    public ExportList create(List<Integer> journalIds, List<Integer> taskIds) throws ExportException, PrintableExportException {
        ExportList exportList = new ExportList();

        this.fillExportByType(exportList, StrategyConstants.JOURNAL_TYPE, journalIds);
        this.fillExportByType(exportList, StrategyConstants.TASK_TYPE, taskIds);

        return exportList;
    }

    private void fillExportByType(ExportList exportList, String type, List<Integer> ids) throws ExportException, PrintableExportException {
        ExportConfigItem configItem = ExportConfig.getInstance().get(type);
        ExportStrategy exportStrategy = ExportStrategyHelper.getInstance().resolveStrategy(configItem);
        try {
            if (exportStrategy == null) throw
                    new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } catch (StrategyNotFoundException e) {
            throw new PrintableExportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        exportStrategy.collectExportIds(exportList, ids);
    }
}
