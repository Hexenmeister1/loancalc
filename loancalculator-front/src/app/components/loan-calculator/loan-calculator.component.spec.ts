import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { LoanCalculatorComponent } from './loan-calculator.component';
import { LoanService } from '../../services/loan.service';

describe('LoanCalculatorComponent', () => {
    let component: LoanCalculatorComponent;
    let fixture: ComponentFixture<LoanCalculatorComponent>;
    let loanService: jasmine.SpyObj<LoanService>;

    beforeEach(async () => {
        const loanServiceSpy = jasmine.createSpyObj('LoanService', ['calculateAmortizationSchedule']);

        await TestBed.configureTestingModule({
            imports: [LoanCalculatorComponent, ReactiveFormsModule, HttpClientTestingModule],
            providers: [
                { provide: LoanService, useValue: loanServiceSpy }
            ]
        }).compileComponents();

        loanService = TestBed.inject(LoanService) as jasmine.SpyObj<LoanService>;
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(LoanCalculatorComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize the form with default values', () => {
        expect(component.loanForm.get('loanAmount')?.value).toBe(100000);
        expect(component.loanForm.get('interestRate')?.value).toBe(2.12);
        expect(component.loanForm.get('initialRepaymentRate')?.value).toBe(2.0);
        expect(component.loanForm.get('interestFixationYears')?.value).toBe(10);
    });

    it('should mark form as invalid when required fields are empty', () => {
        component.loanForm.get('loanAmount')?.setValue(null);
        expect(component.loanForm.valid).toBeFalse();
    });

    it('should call loan service on valid form submission', () => {
        const mockResponse = {
            entries: [],
            totalInterestPaid: 0,
            totalRepaymentPaid: 0,
            finalRemainingDebt: 0
        };
        loanService.calculateAmortizationSchedule.and.returnValue(of(mockResponse));

        component.onSubmit();
        expect(loanService.calculateAmortizationSchedule).toHaveBeenCalled();
        expect(component.loading).toBeFalse();
        expect(component.scheduleResult).toEqual(mockResponse);
    });

    it('should handle errors from loan service', () => {
        loanService.calculateAmortizationSchedule.and.returnValue(
            throwError(() => new Error('Calculation failed'))
        );

        component.onSubmit();
        expect(component.error).toBe('Calculation failed');
        expect(component.loading).toBeFalse();
    });
});
