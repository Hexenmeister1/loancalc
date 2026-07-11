package com.europace.loancalculator.integration;

import com.europace.loancalculator.controller.LoanController;
import com.europace.loancalculator.dto.LoanRequest;
import com.europace.loancalculator.dto.LoanScheduleResponse;
import com.europace.loancalculator.service.LoanCalculationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoanCalculationIntegrationTest {

    @Test
    void shouldCalculateAndReturnAmortizationSchedule() {
        LoanCalculationService service = mock(LoanCalculationService.class);
        LoanController controller = new LoanController(service);

        LoanRequest request = new LoanRequest(
            BigDecimal.valueOf(100000),
            BigDecimal.valueOf(2.12),
            BigDecimal.valueOf(2.0),
            10
        );

        LoanScheduleResponse expectedResponse = new LoanScheduleResponse(
            java.util.List.of(),
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO
        );
        when(service.calculateAmortizationSchedule(request)).thenReturn(expectedResponse);

        ResponseEntity<LoanScheduleResponse> response = controller.calculateAmortizationSchedule(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }
}