package com.europace.loancalculator.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record LoanRequest(
    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "0.01", message = "Loan amount must be greater than 0")
    BigDecimal loanAmount,
    
    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate must be greater than or equal to 0")
    BigDecimal interestRate,
    
    @NotNull(message = "Initial repayment rate is required")
    @DecimalMin(value = "0.0", message = "Initial repayment rate must be greater than or equal to 0")
    BigDecimal initialRepaymentRate,
    
    @NotNull(message = "Interest fixation period is required")
    @Min(value = 1, message = "Interest fixation period must be at least 1 year")
    Integer interestFixationYears
) {}