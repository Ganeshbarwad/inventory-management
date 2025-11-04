package com.gt.inventory_management.controller;

import com.gt.inventory_management.model.TransactionLog;
import com.gt.inventory_management.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class TransactionLogController {

    @Autowired
    private TransactionLogService logService;

    @GetMapping
    public ResponseEntity<List<TransactionLog>> getAllLogs(){

        return ResponseEntity.ok(logService.getAllLogs());
    }
}
