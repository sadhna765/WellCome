import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class MilkCollectionService {
  

  private apiUrl = '/api/milk-collection';
  private farmerUrl = '/api/farmers';

  constructor(private http: HttpClient) {}

  // Farmers list (dropdown ke liye)
  getAllFarmers(): Observable<any[]> {
    return this.http.get<any[]>(this.farmerUrl);
  }

  // Naya collection save
  addCollection(farmerId: number, data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${farmerId}`, data);
  }

  // Saari collections
  getAllCollections(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/today`);
  }

  // Date wise
  getByDate(date: string): Observable<any[]> {
    console.log('Fetching collections for date:', date);
    return this.http.get<any[]>(`${this.apiUrl}/date/${date}`);
  }

  getMergedByDate(date: string): Observable<any[]> {
    console.log('Fetching merged collections for date:', date);
    return this.http.get<any[]>(`${this.apiUrl}/merged/date/${date}`);
  }

  getAllMergedCollections(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/merged`);
  }

  // Single collection
  getById(id: number): Observable<any> {
    console.log('Fetching collection with ID:', id);
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Daily summary
  getDailySummary(date: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/summary/${date}`);
  }

  // Delete
  deleteCollection(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
  
}
