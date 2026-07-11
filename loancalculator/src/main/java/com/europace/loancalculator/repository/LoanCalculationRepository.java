package com.europace.loancalculator.repository;


import com.europace.loancalculator.model.LoanScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanCalculationRepository extends JpaRepository<LoanScheduleEntry, Long> {
    List<LoanScheduleEntry> findByCalculationIdOrderByDate(String calculationId);
    void deleteByCalculationId(String calculationId);
}
