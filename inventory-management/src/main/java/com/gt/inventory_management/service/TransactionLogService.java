package com.gt.inventory_management.service;

import com.gt.inventory_management.model.TransactionLog;
import com.gt.inventory_management.repo.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionLogService {

    @Autowired
    private TransactionLogRepository logRepository;

    public void saveTransaction(TransactionLog log) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            Long userId = ((CustomUserDetails) principal).getId();
            log.setUserId(userId);
        } else {

            log.setUserId(null);
        }

        logRepository.save(log);
    }

    public List<TransactionLog> getAllLogs() {
        return logRepository.findAll();
    }
}
