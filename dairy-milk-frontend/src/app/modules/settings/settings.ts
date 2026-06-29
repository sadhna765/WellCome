import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.html',
  styleUrl: './settings.css'
})
export class SettingsComponent implements OnInit {

  private apiUrl = 'http://localhost:8081/api/settings';

  settings: any = {
    dairyName: '',
    ownerName: '',
    contact: '',
    address: '',
    defaultRate: 0,
    currency: 'Rs'
  };

  loading = false;
  successMsg = '';
  errorMsg = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadSettings();
  }

  loadSettings(): void {
    this.http.get<any>(this.apiUrl).subscribe({
      next: (data) => {
        if (data) this.settings = data;
      },
      error: (err) => console.error('Settings load failed:', err)
    });
  }

  saveSettings(): void {
    this.successMsg = '';
    this.errorMsg = '';

    if (!this.settings.dairyName?.trim()) {
      this.errorMsg = 'Dairy ka naam daalo';
      return;
    }

    this.loading = true;
    this.http.put<any>(this.apiUrl, this.settings).subscribe({
      next: (data) => {
        this.loading = false;
        this.settings = data;
        this.successMsg = 'Settings save ho gayi!';
        // 3 second baad message hata do
        setTimeout(() => this.successMsg = '', 3000);
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = 'Save fail hua';
        console.error('Save failed:', err);
      }
    });
  }
}