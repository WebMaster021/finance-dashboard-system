package com.finance.financedashboard.service;

import com.finance.financedashboard.exception.ResourceNotFoundException;
import com.finance.financedashboard.model.FinancialRecord;
import com.finance.financedashboard.model.User;
import com.finance.financedashboard.model.enums.RecordType;
import com.finance.financedashboard.model.enums.Role;
import com.finance.financedashboard.model.enums.UserStatus;
import com.finance.financedashboard.repository.FinancialRecordRepository;
import com.finance.financedashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DashboardService {

    @Autowired
    private FinancialRecordRepository repo;

    @Autowired
    private UserRepository userRepo;

    private void checkAccess(User user, Role... allowedRoles) {

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ResourceNotFoundException("User is inactive");
        }

        for (Role role : allowedRoles) {
            if (user.getRole() == role) return;
        }

        throw new ResourceNotFoundException("Access Denied");
    }

    public Map<String, Object> summary(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        checkAccess(user, Role.ADMIN, Role.ANALYST, Role.VIEWER);

        List<FinancialRecord> records = repo.findByIsDeletedFalse();

        BigDecimal income = records.stream()
                .filter(r -> r.getType() == RecordType.INCOME)
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = records.stream()
                .filter(r -> r.getType() == RecordType.EXPENSE)
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> res = new HashMap<>();
        res.put("totalIncome", income);
        res.put("totalExpense", expense);
        res.put("balance", income.subtract(expense));

        return res;
    }
}
