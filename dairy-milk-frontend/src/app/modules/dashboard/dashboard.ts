import { HttpClient } from '@angular/common/http';
import { Component, OnInit, signal } from '@angular/core';

interface DashboardStats {
  totalFarmers: number;
  activeFarmers: number;
  totalMilkCollection: number;
  totalPayments: number;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {

  totalFarmers = signal(0);
  activeFarmers = signal(0);
  totalMilk = signal(0);
  totalPayments = signal(0);

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadDashboardStats();
  }

  private loadDashboardStats(): void {
    this.http.get<DashboardStats>('/api/dashboard')
      .subscribe({
        next: (data) => {
          this.totalFarmers.set(data.totalFarmers ?? 0);
          this.activeFarmers.set(data.activeFarmers ?? 0);
          this.totalMilk.set(data.totalMilkCollection ?? 0);
          this.totalPayments.set(data.totalPayments ?? 0);
        },
        error: (err) => console.error('Dashboard load failed:', err)
      });
  }
}
