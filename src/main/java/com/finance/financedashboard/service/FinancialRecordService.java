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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FinancialRecordService {

    @Autowired
    private FinancialRecordRepository recordRepo;

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

    public FinancialRecord create(UUID userId, FinancialRecord record) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        checkAccess(user, Role.ADMIN);

        record.setUser(user);
        return recordRepo.save(record);
    }

    public List<FinancialRecord> getAll(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        checkAccess(user, Role.ADMIN, Role.ANALYST);
        return recordRepo.findByIsDeletedFalse();
    }

    public void delete(UUID id, UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        checkAccess(user, Role.ADMIN);

        FinancialRecord r = recordRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        r.setIsDeleted(true);
        recordRepo.save(r);
    }

    public FinancialRecord update(UUID userId, UUID id, FinancialRecord updated) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        checkAccess(user, Role.ADMIN);

        FinancialRecord record = recordRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        record.setAmount(updated.getAmount());
        record.setType(updated.getType());
        record.setCategory(updated.getCategory());
        record.setRecordDate(updated.getRecordDate());
        record.setNotes(updated.getNotes());

        return recordRepo.save(record);
    }

    public List<FinancialRecord> getFiltered(
            UUID userId,
            RecordType type,
            String category,
            LocalDate from,
            LocalDate to
    ) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        checkAccess(user, Role.ADMIN, Role.ANALYST);

        List<FinancialRecord> records = recordRepo.findByIsDeletedFalse();

        return records.stream()
                .filter(r -> type == null || r.getType() == type)
                .filter(r -> category == null || r.getCategory().equalsIgnoreCase(category))
                .filter(r -> from == null || !r.getRecordDate().isBefore(from))
                .filter(r -> to == null || !r.getRecordDate().isAfter(to))
                .toList();
    }
}
