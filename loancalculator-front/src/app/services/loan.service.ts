import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { LoanRequest } from '../models/loan-request.model';
import { LoanScheduleResponse } from '../models/loan-schedule.model';
import { environment } from '../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class LoanService {
    private apiUrl = `${environment.apiUrl}/api/loans`;

    constructor(private http: HttpClient) {}

    calculateAmortizationSchedule(request: LoanRequest): Observable<LoanScheduleResponse> {
        return this.http.post<LoanScheduleResponse>(
            `${this.apiUrl}/calculate`,
            request
        ).pipe(
            retry(2),
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse) {
        let errorMessage = 'An error occurred during the calculation. Please try again.';

        if (error.error instanceof ErrorEvent) {
            // Client-side error
            errorMessage = error.error.message;
        } else {
            // Server-side error
            if (error.status === 400 && error.error) {
                if (error.error.error) {
                    errorMessage = error.error.error;
                } else if (typeof error.error === 'object') {
                    const validationErrors = Object.values(error.error).join(', ');
                    errorMessage = `Validation errors: ${validationErrors}`;
                }
            } else {
                errorMessage = `Error ${error.status}: ${error.message}`;
            }
        }

        return throwError(() => new Error(errorMessage));
    }
}
