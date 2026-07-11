export interface LoanScheduleEntry {
    date: string;
    remainingDebt: number;
    interest: number;
    repayment: number;
    payment: number;
    calculationId?: string;
}

export interface LoanScheduleResponse {
    entries: LoanScheduleEntry[];
    totalInterestPaid: number;
    totalRepaymentPaid: number;
    finalRemainingDebt: number;
}
