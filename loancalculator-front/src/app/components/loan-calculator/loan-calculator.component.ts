import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoanService } from '../../services/loan.service';
import { LoanScheduleResponse } from '../../models/loan-schedule.model';
import { LoanScheduleComponent } from '../loan-schedule/loan-schedule.component';

@Component({
  selector: 'app-loan-calculator',
  standalone: true,
  imports: [ReactiveFormsModule, LoanScheduleComponent],
  templateUrl: './loan-calculator.component.html',
  styleUrls: ['./loan-calculator.component.css'],
})
export class LoanCalculatorComponent implements OnInit {
  loanForm: FormGroup;
  scheduleResult: LoanScheduleResponse | null = null;
  loading = false;
  error: string | null = null;
  showSchedule = false;

  constructor(
    private fb: FormBuilder,
    private loanService: LoanService,
  ) {
    this.loanForm = this.fb.group({
      loanAmount: [100000, [Validators.required, Validators.min(0.01)]],
      interestRate: [2.12, [Validators.required, Validators.min(0)]],
      initialRepaymentRate: [2.0, [Validators.required, Validators.min(0)]],
      interestFixationYears: [10, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.loanForm.invalid) {
      Object.keys(this.loanForm.controls).forEach((key) => {
        const control = this.loanForm.get(key);
        control?.markAsTouched();
      });
      return;
    }

    this.loading = true;
    this.error = null;
    this.showSchedule = false;

    console.log('Submitting loan calculation', this.loanForm.value);
    this.loanService.calculateAmortizationSchedule(this.loanForm.value).subscribe({
      next: (response) => {
        console.log('Calculation response received', response);
        this.scheduleResult = response;
        this.loading = false;
        this.showSchedule = true;
      },
      error: (err) => {
        console.error('Calculation request failed', err);
        this.error = err.message;
        this.loading = false;
        this.showSchedule = false;
      },
    });
  }

  resetForm(): void {
    this.loanForm.reset({
      loanAmount: 100000,
      interestRate: 2.12,
      initialRepaymentRate: 2.0,
      interestFixationYears: 10,
    });
    this.scheduleResult = null;
    this.showSchedule = false;
    this.error = null;
  }

  getErrorMessage(fieldName: string): string {
    const control = this.loanForm.get(fieldName);
    if (control?.errors && control.touched) {
      if (control.errors['required']) {
        return 'This field is required';
      }
      if (control.errors['min']) {
        return `Value must be greater than ${control.errors['min'].min}`;
      }
    }
    return '';
  }
}
