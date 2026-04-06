package com.finance.financedashboard.controller;

import com.finance.financedashboard.model.FinancialRecord;
import com.finance.financedashboard.model.User;
import com.finance.financedashboard.model.enums.RecordType;
import com.finance.financedashboard.service.FinancialRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordService service;

//    Creates record.
//    It takes userId to verify role(ADMIN, VIEWER, ANALYST).
    @PostMapping("/{userId}")
    public FinancialRecord create(@PathVariable UUID userId,
                                  @RequestBody FinancialRecord record) {
        return service.create(userId, record);
    }

//    Get all the records.
//    It takes userId to verify role(ADMIN, VIEWER, ANALYST) and other params for filtering they are not necessary.
    @GetMapping
    public List<FinancialRecord> getAll(
            @RequestParam UUID userId,
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return service.getFiltered(userId, type, category, from, to);
    }

//    Delete existing record.
//    It takes userId to verify role(ADMIN, VIEWER, ANALYST) and uses id as recordId which need to be deleted.
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id,
                       @RequestParam UUID userId) {
        service.delete(id, userId);
    }

//    Update existing record.
//    It takes userId to verify role(ADMIN, VIEWER, ANALYST) and uses id as recordId which need to be updated.
    @PutMapping("/{id}")
    public FinancialRecord update(@RequestParam UUID userId,
                                  @PathVariable UUID id,
                                  @RequestBody FinancialRecord record) {
        return service.update(userId, id, record);
    }
}
