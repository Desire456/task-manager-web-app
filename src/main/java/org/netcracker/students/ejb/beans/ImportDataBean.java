package org.netcracker.students.ejb.beans;

import org.netcracker.students.ejb.interfaces.ImportData;
import org.netcracker.students.ejb.strategy.importing.ImportStrategy;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ImportDataBean implements ImportData {
    private ImportStrategy importStrategy;

    public void setImportStrategy(ImportStrategy importStrategy) {
        this.importStrategy = importStrategy;
    }

    public List<Object> importData(String xml) {
        return importStrategy.importData(xml);
    }
}
