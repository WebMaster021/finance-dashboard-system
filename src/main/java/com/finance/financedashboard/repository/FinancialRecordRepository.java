package com.finance.financedashboard.repository;

import com.finance.financedashboard.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, UUID> {
    List<FinancialRecord> findByIsDeletedFalse();
    List<FinancialRecord> findByUserIdAndIsDeletedFalse(UUID userId);
}
