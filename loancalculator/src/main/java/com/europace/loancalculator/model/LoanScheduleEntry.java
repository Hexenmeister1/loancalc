package com.europace.loancalculator.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan_schedule_entries")
public class LoanScheduleEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal remainingDebt;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal interest;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal repayment;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal payment;
    
    @Column(nullable = false)
    private String calculationId;
    
    public LoanScheduleEntry() {}
    
    public LoanScheduleEntry(LocalDate date, BigDecimal remainingDebt, BigDecimal interest, 
                           BigDecimal repayment, BigDecimal payment, String calculationId) {
        this.date = date;
        this.remainingDebt = remainingDebt;
        this.interest = interest;
        this.repayment = repayment;
        this.payment = payment;
        this.calculationId = calculationId;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public BigDecimal getRemainingDebt() { return remainingDebt; }
    public void setRemainingDebt(BigDecimal remainingDebt) { this.remainingDebt = remainingDebt; }
    
    public BigDecimal getInterest() { return interest; }
    public void setInterest(BigDecimal interest) { this.interest = interest; }
    
    public BigDecimal getRepayment() { return repayment; }
    public void setRepayment(BigDecimal repayment) { this.repayment = repayment; }
    
    public BigDecimal getPayment() { return payment; }
    public void setPayment(BigDecimal payment) { this.payment = payment; }
    
    public String getCalculationId() { return calculationId; }
    public void setCalculationId(String calculationId) { this.calculationId = calculationId; }
}