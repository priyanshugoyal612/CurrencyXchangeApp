import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CurrencyServiceService {

  private baseUrl = 'http://localhost:8080/v1/api';

  constructor(private http:HttpClient) { }

    currencySubject= new BehaviorSubject<number>(0);

    getXchangeRate(fromCurrency: string, toCurrency: string, amount:number):Observable<any>{
      const  url =  `${this.baseUrl}/exchange?fromCurrency=${fromCurrency}&toCurrency=${toCurrency}&amount=${amount}`;
      return this.http.get<number>(url);


    }

    getAllCurrencies(): Observable<any[]> {
      const  url =  `${this.baseUrl}/currencies`;
      return this.http.get<any[]>(url);
    }

    getCurrencyExchangeRate(): Observable<number> {
      return this.currencySubject.asObservable();
    }

     // Method to update the currency exchange rate
  updateCurrencyExchangeRate(rate: number): void {
    this.currencySubject.next(rate);
  }
 
}
