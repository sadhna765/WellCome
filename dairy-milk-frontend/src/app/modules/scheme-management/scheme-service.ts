import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Scheme } from './scheme-model';


@Injectable({ providedIn: 'root' })
export class SchemeService {
  private apiUrl = 'http://localhost:8081/api/schemes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Scheme[]> {
    return this.http.get<Scheme[]>(this.apiUrl);
  }

  getById(id: number): Observable<Scheme> {
    return this.http.get<Scheme>(`${this.apiUrl}/${id}`);
  }

  create(scheme: Scheme): Observable<Scheme> {
    return this.http.post<Scheme>(this.apiUrl, scheme);
  }

  update(id: number, scheme: Scheme): Observable<Scheme> {
    return this.http.put<Scheme>(`${this.apiUrl}/${id}`, scheme);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}