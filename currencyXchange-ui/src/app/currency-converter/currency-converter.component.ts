import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CurrencyServiceService } from '../services/currency/currency-service.service';
import { EMPTY, catchError, tap } from 'rxjs';



@Component({
  selector: 'app-currency-converter',
  standalone: true,
  imports: [
    MatCardModule, MatButtonModule, MatInputModule,MatSelectModule,CommonModule,FormsModule
  ],
  templateUrl: './currency-converter.component.html',
  styleUrl: './currency-converter.component.css'
})
export class CurrencyConverterComponent  implements OnInit{
  ngOnInit(): void {
    this.getConversionRate();
    console.log(this.getCurrencies());
    this.currencyService.currencySubject.subscribe()
  }

  constructor(private currencyService: CurrencyServiceService) { }

  // currencies = [
  //   { value: 'USD', name: 'US Dollar' },
  //   { value: 'EUR', name: 'Euro' },
  //   { value: 'INR', name: 'Indian Ruppes' },
  //   { value: 'JPY', name: 'Japanese Yen' }
  // ];
  currencies: any[] = [];

  selectedFromCurrency: string = 'EUR';
  selectedToCurrency: string = 'INR';
  amount: number =1;
  convertedAmount: number | undefined;
  errorMessage: string | undefined;

  convert() {
    // Your conversion logic goes here
    // For simplicity, let's just do a straightforward conversion
   // const conversionRate = this.getConversionRate();
   console.log('hit');
   this.errorMessage=undefined;
  //  this.convertedAmount=
    this.getConversionRate();
  }

  getCurrencies() {
    this.currencyService.getAllCurrencies().
    pipe(
      tap( data => {
        this.currencies = data.map(currency => ({ value: currency.code, name: currency.name }));
      }),
      catchError(() => {
        console.error('Error fetching exchange rate.');
        this.errorMessage = 'Will be right back'; // Set error message
        
        return EMPTY; // Return empty Observable to suppress error
      })
    ).subscribe();
  }

  getConversionRate() {    
    this.currencyService.getXchangeRate(this.selectedFromCurrency, this.selectedToCurrency, this.amount)
      .pipe(
        tap ( data => {
          this.convertedAmount = data.convertedAmount; // Extract converted amount from API response
        }),
        catchError((error) => {
          console.error('Error fetching exchange rate.',error);
          if (error.status === 403) {
            this.errorMessage = 'Some of the Currencies are not supported for developer plan, please upgrade';
          } else if (error.status === 400) {
            this.errorMessage = 'Invalid request. Please check your inputs.';
          } else {
            this.errorMessage = 'An unexpected error occurred while fetching exchange rate. Please contact administrator';
          }
          
          return EMPTY; // Return empty Observable to suppress error
        })
      )
      .subscribe();
    }
}
