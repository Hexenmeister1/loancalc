package com.europace.loancalculator.controller;


import com.europace.loancalculator.dto.LoanRequest;
import com.europace.loancalculator.dto.LoanScheduleResponse;
import com.europace.loancalculator.service.LoanCalculationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"}, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class LoanController {
    
    private final LoanCalculationService loanCalculationService;
    
    public LoanController(LoanCalculationService loanCalculationService) {
        this.loanCalculationService = loanCalculationService;
    }
    
    @PostMapping("/calculate")
    public ResponseEntity<LoanScheduleResponse> calculateAmortizationSchedule(
            @Valid @RequestBody LoanRequest request) {
        LoanScheduleResponse response = loanCalculationService.calculateAmortizationSchedule(request);
        return ResponseEntity.ok(response);
    }
}