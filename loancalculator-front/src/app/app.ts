import { Component, signal } from '@angular/core';
import { LoanCalculatorComponent } from './components/loan-calculator/loan-calculator.component';

@Component({
  selector: 'app-root',
  imports: [LoanCalculatorComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('loancalculator-front');
}
