
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class Farmer  {

  private apiUrl = '/api/farmers';
  constructor(private http: HttpClient) {}

  getFarmers(): Observable<any[]> {
    return this.http.get<any[] | { value?: any[] }>(this.apiUrl).pipe(
      map((response) => Array.isArray(response) ? response : response.value ?? [])
    );
  }

  addFarmer(farmerData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, farmerData);
  }

  updateFarmer(id: string | number, farmerData: any): Observable<any> {
    console.log('Updating Farmer:', farmerData);
    return this.http.put<any>(`${this.apiUrl}/${id}`, farmerData);
  }

}
