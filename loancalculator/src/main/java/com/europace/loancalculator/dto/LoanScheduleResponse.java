package com.europace.loancalculator.dto;

import com.europace.loancalculator.model.LoanScheduleEntry;
import java.math.BigDecimal;
import java.util.List;

public record LoanScheduleResponse(
    List<LoanScheduleEntry> entries,
    BigDecimal totalInterestPaid,
    BigDecimal totalRepaymentPaid,
    BigDecimal finalRemainingDebt
) {}