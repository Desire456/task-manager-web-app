package org.netcracker.students.ejb.strategy.exporting;

import java.util.List;

public interface ExportStrategy {
    String exportData(List<Object> object);
}
