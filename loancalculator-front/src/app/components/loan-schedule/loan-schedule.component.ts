import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { LoanScheduleResponse, LoanScheduleEntry } from '../../models/loan-schedule.model';

@Component({
    selector: 'app-loan-schedule',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './loan-schedule.component.html',
    styleUrls: ['./loan-schedule.component.css']
})
export class LoanScheduleComponent implements OnChanges {
    @Input() schedule!: LoanScheduleResponse;

    displayedColumns: string[] = ['date', 'remainingDebt', 'interest', 'repayment', 'payment'];
    currentPage = 1;
    itemsPerPage = 12;
    paginatedEntries: LoanScheduleEntry[] = [];

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['schedule'] && this.schedule) {
            this.currentPage = 1;
            this.recomputePaginatedEntries();
        }
    }

    private recomputePaginatedEntries(): void {
        if (!this.schedule || !this.schedule.entries) {
            this.paginatedEntries = [];
            return;
        }
        const start = (this.currentPage - 1) * this.itemsPerPage;
        const end = start + this.itemsPerPage;
        this.paginatedEntries = this.schedule.entries.slice(start, end);
    }

    get totalPages(): number {
        if (!this.schedule || !this.schedule.entries) {
            return 0;
        }
        return Math.ceil(this.schedule.entries.length / this.itemsPerPage);
    }

    get pageNumbers(): number[] {
        return Array.from({ length: this.totalPages }, (_, i) => i + 1);
    }

    goToPage(page: number): void {
        if (page >= 1 && page <= this.totalPages) {
            this.currentPage = page;
            this.recomputePaginatedEntries();
        }
    }

    formatCurrency(value: number): string {
        return new Intl.NumberFormat('de-DE', {
            style: 'currency',
            currency: 'EUR',
            minimumFractionDigits: 2
        }).format(value);
    }

    formatDate(dateString: string): string {
        const date = new Date(dateString);
        return date.toLocaleDateString('de-DE', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit'
        });
    }
}
