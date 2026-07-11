package com.europace.loancalculator.service;


import com.europace.loancalculator.dto.LoanRequest;
import com.europace.loancalculator.dto.LoanScheduleResponse;
import com.europace.loancalculator.exception.LoanCalculationException;
import com.europace.loancalculator.model.LoanScheduleEntry;
import com.europace.loancalculator.repository.LoanCalculationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LoanCalculationServiceImpl implements LoanCalculationService {
    
    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);
    private final LoanCalculationRepository repository;
    
    public LoanCalculationServiceImpl(LoanCalculationRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public LoanScheduleResponse calculateAmortizationSchedule(LoanRequest request) {
        validateRequest(request);
        
        String calculationId = UUID.randomUUID().toString();
        List<LoanScheduleEntry> entries = new ArrayList<>();
        
        BigDecimal loanAmount = request.loanAmount();
        BigDecimal monthlyInterestRate = request.interestRate()
            .divide(BigDecimal.valueOf(1200), MC);
        BigDecimal monthlyRepaymentRate = request.initialRepaymentRate()
            .divide(BigDecimal.valueOf(1200), MC);
        
        // Calculate initial monthly payment
        BigDecimal monthlyPayment = loanAmount
            .multiply(monthlyInterestRate.add(monthlyRepaymentRate))
            .setScale(2, RoundingMode.HALF_UP);
        
        LocalDate currentDate = LocalDate.now();
        LocalDate payoutDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        
        // Payout entry
        entries.add(createEntry(payoutDate, loanAmount.negate(), 
            BigDecimal.ZERO, loanAmount.negate(), loanAmount.negate(), calculationId));
        
        BigDecimal remainingDebt = loanAmount;
        LocalDate paymentDate = payoutDate.plusMonths(1);
        BigDecimal totalInterest = BigDecimal.ZERO;
        BigDecimal totalRepayment = BigDecimal.ZERO;
        
        int monthsInFixation = request.interestFixationYears() * 12;
        int currentMonth = 0;
        
        while (currentMonth < monthsInFixation && remainingDebt.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal monthlyInterest = remainingDebt
                .multiply(monthlyInterestRate)
                .setScale(2, RoundingMode.HALF_UP);
            
            BigDecimal monthlyRepayment = monthlyPayment.subtract(monthlyInterest);
            
            // Ensure we don't overpay
            if (monthlyRepayment.compareTo(remainingDebt) > 0) {
                monthlyRepayment = remainingDebt;
                monthlyPayment = monthlyInterest.add(monthlyRepayment);
            }
            
            BigDecimal newRemainingDebt = remainingDebt.subtract(monthlyRepayment);
            
            entries.add(createEntry(paymentDate, newRemainingDebt, monthlyInterest, 
                monthlyRepayment, monthlyPayment, calculationId));
            
            totalInterest = totalInterest.add(monthlyInterest);
            totalRepayment = totalRepayment.add(monthlyRepayment);
            remainingDebt = newRemainingDebt;
            
            paymentDate = paymentDate.plusMonths(1);
            currentMonth++;
        }
        
        // Save to repository
        repository.saveAll(entries);
        
        return new LoanScheduleResponse(
            entries,
            totalInterest.setScale(2, RoundingMode.HALF_UP),
            totalRepayment.setScale(2, RoundingMode.HALF_UP),
            remainingDebt.setScale(2, RoundingMode.HALF_UP)
        );
    }
    
    private LoanScheduleEntry createEntry(LocalDate date, BigDecimal remainingDebt, 
                                        BigDecimal interest, BigDecimal repayment, 
                                        BigDecimal payment, String calculationId) {
        return new LoanScheduleEntry(
            date,
            remainingDebt.setScale(2, RoundingMode.HALF_UP),
            interest.setScale(2, RoundingMode.HALF_UP),
            repayment.setScale(2, RoundingMode.HALF_UP),
            payment.setScale(2, RoundingMode.HALF_UP),
            calculationId
        );
    }
    
    private void validateRequest(LoanRequest request) {
        if (request.loanAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new LoanCalculationException("Loan amount must be greater than 0");
        }
        if (request.interestRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new LoanCalculationException("Interest rate must be greater than or equal to 0");
        }
        if (request.initialRepaymentRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new LoanCalculationException("Initial repayment rate must be greater than or equal to 0");
        }
        if (request.interestFixationYears() < 1) {
            throw new LoanCalculationException("Interest fixation period must be at least 1 year");
        }
    }
}