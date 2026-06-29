import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rate, MilkType } from './rate';

@Injectable({ providedIn: 'root' })
export class RateService {

  private base = '/api/rates';

  constructor(private http: HttpClient) {}

  getAll(milkType?: MilkType): Observable<Rate[]> {
    const url = milkType ? `${this.base}?milkType=${milkType}` : this.base;
    return this.http.get<Rate[]>(url);
  }

  create(rate: Rate): Observable<Rate> {
    return this.http.post<Rate>(this.base, rate);
  }

  update(id: number, rate: Rate): Observable<Rate> {
    return this.http.put<Rate>(`${this.base}/${id}`, rate);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }

  lookup(milkType: MilkType, fat: number): Observable<{ milkType: MilkType; fat: number; rate: number }> {
    return this.http.get<{ milkType: MilkType; fat: number; rate: number }>(
      `${this.base}/lookup?milkType=${milkType}&fat=${fat}`
    );
  }
}
