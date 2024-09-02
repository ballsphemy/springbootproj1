package com.aclc.InventoryManagementSystem.service;

import com.aclc.InventoryManagementSystem.model.Logs;
import com.aclc.InventoryManagementSystem.repository.LogsRepository;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsRepository logsRepository;

    @Override
    public void logAction(String username, String action) {
        Logs log = new Logs();
        log.setUsername(username);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());

        logsRepository.save(log);
    }

    @Override
    public List<Logs> getAllLogs() {
        return logsRepository.findAll();
    }
    @Override
    public void saveLogsToExcel(ByteArrayOutputStream outputStream) throws IOException {
        List<Logs> logsList = getAllLogs();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Logs");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Username");
            headerRow.createCell(2).setCellValue("Action");
            headerRow.createCell(3).setCellValue("Timestamp");

            // Populate rows with log data
            int rowNum = 1;
            for (Logs log : logsList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(log.getId());
                row.createCell(1).setCellValue(log.getUsername());
                row.createCell(2).setCellValue(log.getAction());
                row.createCell(3).setCellValue(log.getTimestamp().toString());
            }

            // Write the workbook data to the ByteArrayOutputStream
            workbook.write(outputStream);
        }
    }

}
