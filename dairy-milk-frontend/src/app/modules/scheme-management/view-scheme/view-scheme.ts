import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SchemeService } from '../scheme-service';
import { Scheme } from '../scheme-model';

@Component({
  selector: 'app-view-scheme',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-scheme.html',
  styleUrls: ['./view-scheme.css']
})
export class ViewScheme implements OnInit {
  schemes: Scheme[] = [];
  loading = false;

  constructor(private schemeService: SchemeService, private router: Router) {}

  ngOnInit(): void {
    this.loadSchemes();
  }

  loadSchemes(): void {
    this.loading = true;
    this.schemeService.getAll().subscribe({
      next: (data) => { this.schemes = data; this.loading = false; },
      error: (err) => { console.error(err); this.loading = false; }
    });
  }

  addNew(): void {
    this.router.navigate(['/schemes/add']);
  }

  editScheme(id?: number): void {
    if (id != null) this.router.navigate(['/schemes/edit', id]);
  }

  deleteScheme(id?: number): void {
    if (id == null) return;
    if (!confirm('Is scheme ko delete karein?')) return;
    this.schemeService.delete(id).subscribe({
      next: () => this.loadSchemes(),
      error: (err) => console.error(err)
    });
  }
}