import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SchemeService } from '../scheme-service';
import { Scheme } from '../scheme-model';

@Component({
  selector: 'app-add-scheme',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-scheme.html',
  styleUrls: ['./add-scheme.css']
})
export class AddScheme implements OnInit {
  scheme: Scheme = {
    schemeName: '',
    schemeType: '',
    description: '',
    valueType: 'FIXED',
    value: 0,
    startDate: '',
    endDate: '',
    active: true
  };

  isEdit = false;
  editId?: number;

  constructor(
    private schemeService: SchemeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.editId = +id;
      this.schemeService.getById(this.editId).subscribe({
        next: (data) => this.scheme = data,
        error: (err) => console.error(err)
      });
    }
  }

  saveScheme(): void {
    if (!this.scheme.schemeName || !this.scheme.schemeType) {
      alert('Scheme name aur type zaroori hai');
      return;
    }

    const req = this.isEdit && this.editId != null
      ? this.schemeService.update(this.editId, this.scheme)
      : this.schemeService.create(this.scheme);

    req.subscribe({
      next: () => this.router.navigate(['/schemes']),
      error: (err) => console.error(err)
    });
  }

  cancel(): void {
    this.router.navigate(['/schemes']);
  }
}