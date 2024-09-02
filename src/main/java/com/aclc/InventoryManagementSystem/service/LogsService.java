package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.model.Logs;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.util.List;

public interface LogsService {
    public void logAction(String username, String action);
    public List<Logs> getAllLogs();
    public void saveLogsToExcel(ByteArrayOutputStream outputStream) throws IOException;
}
