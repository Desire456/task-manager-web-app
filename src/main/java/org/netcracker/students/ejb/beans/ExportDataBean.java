package org.netcracker.students.ejb.beans;


import org.netcracker.students.ejb.interfaces.ExportData;
import org.netcracker.students.ejb.strategy.exporting.ExportStrategy;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ExportDataBean implements ExportData {
    private ExportStrategy exportStrategy;

    public void setExportStrategy(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    public Object export(List<Object> object) { //2-nd parameter strategy
        return exportStrategy.exportData(object);
    }
}
