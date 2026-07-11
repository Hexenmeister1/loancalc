package com.europace.loancalculator.service;


import com.europace.loancalculator.dto.LoanRequest;
import com.europace.loancalculator.dto.LoanScheduleResponse;
import com.europace.loancalculator.exception.LoanCalculationException;
import com.europace.loancalculator.repository.LoanCalculationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class LoanCalculationServiceTest {
    
    private LoanCalculationService loanCalculationService;
    
    @Mock
    private LoanCalculationRepository repository;
    
    @BeforeEach
    void setUp() {
        loanCalculationService = new LoanCalculationServiceImpl(repository);
    }
    
    @Test
    void shouldCalculateAmortizationScheduleCorrectly() {
        // Given
        LoanRequest request = new LoanRequest(
            BigDecimal.valueOf(100000),
            BigDecimal.valueOf(2.12),
            BigDecimal.valueOf(2.0),
            10
        );
        
        // When
        LoanScheduleResponse response = loanCalculationService.calculateAmortizationSchedule(request);
        
        // Then
        assertThat(response).isNotNull();
        assertThat(response.entries()).isNotEmpty();
        assertThat(response.entries().get(0).getRemainingDebt()).isNegative(); // Payout
        assertThat(response.finalRemainingDebt()).isPositive();
        
        // Verify monthly payment consistency (should be around 343.33 EUR)
        BigDecimal firstRepayment = response.entries().get(1).getRepayment();
        BigDecimal firstInterest = response.entries().get(1).getInterest();
        assertThat(firstRepayment.add(firstInterest))
            .isEqualByComparingTo(BigDecimal.valueOf(343.33));
    }
    
    @Test
    void shouldThrowExceptionWhenLoanAmountIsNegative() {
        // Given
        LoanRequest request = new LoanRequest(
            BigDecimal.valueOf(-1000),
            BigDecimal.valueOf(2.0),
            BigDecimal.valueOf(2.0),
            10
        );
        
        // When & Then
        assertThatThrownBy(() -> loanCalculationService.calculateAmortizationSchedule(request))
            .isInstanceOf(LoanCalculationException.class)
            .hasMessageContaining("Loan amount must be greater than 0");
    }
}