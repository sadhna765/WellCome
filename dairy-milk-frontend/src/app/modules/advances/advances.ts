import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-advances',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './advances.html',
  styleUrl: './advances.css'
})
export class AdvancesComponent implements OnInit {

  private apiBase = 'http://localhost:8081/api';

  advances: any[] = [];
  farmers: any[] = [];

  // Form fields
  selectedFarmerId: number | null = null;
  amount: number | null = null;
  reason: string = '';
  advanceDate: string = new Date().toISOString().split('T')[0]; // aaj ki date

  loading = false;
  errorMsg = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadFarmers();
    this.loadAdvances();
  }

  loadFarmers(): void {
    this.http.get<any[]>(`${this.apiBase}/farmers`).subscribe({
      next: (data) => this.farmers = data,
      error: (err) => console.error('Farmers load failed:', err)
    });
  }

  loadAdvances(): void {
    this.http.get<any[]>(`${this.apiBase}/advances`).subscribe({
      next: (data) => this.advances = data,
      error: (err) => console.error('Advances load failed:', err)
    });
  }

  saveAdvance(): void {
    this.errorMsg = '';

    if (!this.selectedFarmerId) {
      this.errorMsg = 'Farmer select karo';
      return;
    }
    if (!this.amount || this.amount <= 0) {
      this.errorMsg = 'Valid amount daalo';
      return;
    }

    const body = {
      amount: this.amount,
      reason: this.reason,
      advanceDate: this.advanceDate
    };

    this.loading = true;
    this.http.post(`${this.apiBase}/advances/farmer/${this.selectedFarmerId}`, body)
      .subscribe({
        next: () => {
          this.loading = false;
          this.resetForm();
          this.loadAdvances();   // list refresh
        },
        error: (err) => {
          this.loading = false;
          this.errorMsg = 'Save fail hua';
          console.error('Save failed:', err);
        }
      });
  }

  deleteAdvance(id: number): void {
    if (!confirm('Ye advance delete karein?')) return;

    this.http.delete(`${this.apiBase}/advances/${id}`).subscribe({
      next: () => this.loadAdvances(),
      error: (err) => console.error('Delete failed:', err)
    });
  }

  resetForm(): void {
    this.selectedFarmerId = null;
    this.amount = null;
    this.reason = '';
    this.advanceDate = new Date().toISOString().split('T')[0];
  }

  // Farmer ka naam nikalne ke liye (agar entity nested farmer object deta hai)
  getFarmerName(adv: any): string {
    return adv.farmerName || adv.farmer?.name || '—';
  }
}