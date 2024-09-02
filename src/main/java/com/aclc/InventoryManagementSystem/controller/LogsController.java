package com.aclc.InventoryManagementSystem.controller;

import com.aclc.InventoryManagementSystem.model.Logs;
import com.aclc.InventoryManagementSystem.service.LogsService;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/api/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;

    @GetMapping
    public ResponseEntity<List<Logs>> getAllLogs() {
        List<Logs> logs = logsService.getAllLogs();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportLogsToExcel() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            logsService.saveLogsToExcel(outputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=logs.xlsx");
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/logaction")
    public ResponseEntity<String> logAction(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String action = request.get("action");
        try {
            logsService.logAction(username, action);
            return ResponseEntity.ok("Log action saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save log action");
        }
    }
}
