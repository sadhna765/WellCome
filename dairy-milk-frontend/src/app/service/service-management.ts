import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ServiceManagement {

  private apiUrl = '/api/payments';

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<any[]>(this.apiUrl);
  }

  save(data: any) {
    return this.http.post(this.apiUrl, data);
  }
}
