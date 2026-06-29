import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users.html',
  styleUrl: './users.css'
})
export class UsersComponent implements OnInit {

  private apiUrl = 'http://localhost:8081/api/users';

  users: any[] = [];
  roles = ['ADMIN', 'OPERATOR', 'ACCOUNTANT'];

  // Form fields
  name = '';
  username = '';
  password = '';
  role = 'OPERATOR';

  loading = false;
  errorMsg = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.http.get<any[]>(this.apiUrl).subscribe({
      next: (data) => this.users = data,
      error: (err) => console.error('Users load failed:', err)
    });
  }

  saveUser(): void {
    this.errorMsg = '';

    if (!this.name.trim()) { this.errorMsg = 'Naam daalo'; return; }
    if (!this.username.trim()) { this.errorMsg = 'Username daalo'; return; }
    if (!this.password.trim()) { this.errorMsg = 'Password daalo'; return; }

    const body = {
      name: this.name,
      username: this.username,
      password: this.password,
      role: this.role,
      active: true
    };

    this.loading = true;
    this.http.post(this.apiUrl, body).subscribe({
      next: () => {
        this.loading = false;
        this.resetForm();
        this.loadUsers();
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = err?.error?.message || 'Save fail hua (username already exist ho sakta hai)';
        console.error('Save failed:', err);
      }
    });
  }

  deleteUser(id: number): void {
    if (!confirm('Ye user delete karein?')) return;

    this.http.delete(`${this.apiUrl}/${id}`).subscribe({
      next: () => this.loadUsers(),
      error: (err) => console.error('Delete failed:', err)
    });
  }

  resetForm(): void {
    this.name = '';
    this.username = '';
    this.password = '';
    this.role = 'OPERATOR';
  }
}