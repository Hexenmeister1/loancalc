package com.europace.loancalculator.service;


import com.europace.loancalculator.dto.LoanRequest;
import com.europace.loancalculator.dto.LoanScheduleResponse;

public interface LoanCalculationService {
    LoanScheduleResponse calculateAmortizationSchedule(LoanRequest request);
}
