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
    this.currencyService.currencySubject.subscribe()
  }

  constructor(private currencyService: CurrencyServiceService) { }

  currencies = [
    { value: 'USD', name: 'US Dollar' },
    { value: 'EUR', name: 'Euro' },
    { value: 'INR', name: 'Indian Ruppes' },
    { value: 'JPY', name: 'Japanese Yen' }
  ];

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

  // Example conversion rates (replace with actual rates)
  // getConversionRate(): number {
  //   const conversionRates: { [key: string]: { [key: string]: number } } = {
  //     'USD': { 'EUR': 0.85, 'GBP': 0.73, 'JPY': 109.81 },
  //     'EUR': { 'USD': 1.18, 'GBP': 0.86, 'JPY': 130.74 },
  //     'GBP': { 'USD': 1.38, 'EUR': 1.16, 'JPY': 152.54 },
  //     'JPY': { 'USD': 0.0091, 'EUR': 0.0076, 'GBP': 0.0066 }
  //   };
  
  //   return conversionRates[this.selectedFromCurrency][this.selectedToCurrency];
  // }

  getConversionRate() {
    
    this.currencyService.getXchangeRate(this.selectedFromCurrency, this.selectedToCurrency, this.amount)
      .pipe(
        tap ( data => {
          this.convertedAmount = data.convertedAmount; // Extract converted amount from API response
        }),
        catchError(() => {
          console.error('Error fetching exchange rate.');
          this.errorMessage = 'Currently we are only supporting basic currency'; // Set error message
          
          return EMPTY; // Return empty Observable to suppress error
        })
      )
      .subscribe();
    }
}
