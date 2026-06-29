import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CattleFeedService {

  private apiUrl = '/api/cattle-feed';

  constructor(private http: HttpClient) {}

  save(farmerId: number, data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${farmerId}`, data);
  }

  getByFarmer(farmerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/farmer/${farmerId}`);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}