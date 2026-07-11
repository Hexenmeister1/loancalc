import { Component } from '@angular/core';
import { LoanCalculatorComponent } from './components/loan-calculator/loan-calculator.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [LoanCalculatorComponent],
  template: '<app-loan-calculator></app-loan-calculator>'
})
export class AppComponent {}
