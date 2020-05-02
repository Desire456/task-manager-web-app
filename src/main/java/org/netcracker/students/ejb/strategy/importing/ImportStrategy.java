package org.netcracker.students.ejb.strategy.importing;

import java.util.List;

public interface ImportStrategy {
    List<Object> importData(String xml);
}
