import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Rate, MilkType } from './rate';
import { RateService } from './rate-service';

@Component({
  selector: 'app-rate-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './rate-management.html'
})
export class RateManagement implements OnInit {

  rates: Rate[] = [];
  filterType: MilkType | '' = '';
  loading = false;
  editingId: number | null = null;

  form: Rate = this.empty();

  constructor(private rateService: RateService) {}

  ngOnInit(): void {
    this.loadRates();
  }

  empty(): Rate {
    return { milkType: 'COW', minFat: 0, maxFat: 0, ratePerLitre: 0, active: true };
  }

  loadRates(): void {
    this.loading = true;
    this.rateService.getAll(this.filterType || undefined).subscribe({
      next: (data) => { this.rates = data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  save(): void {
    if (!this.validate()) return;
    const req = this.editingId
      ? this.rateService.update(this.editingId, this.form)
      : this.rateService.create(this.form);

    req.subscribe({
      next: () => { this.resetForm(); this.loadRates(); },
      error: (e) => alert('Save failed: ' + (e?.error?.message || e.message || 'Unknown error'))
    });
  }

  edit(r: Rate): void {
    this.editingId = r.id ?? null;
    this.form = { ...r };
  }

  remove(r: Rate): void {
    if (!confirm(`Delete ${r.milkType} rate (${r.minFat}–${r.maxFat} fat)?`)) return;
    this.rateService.delete(r.id!).subscribe(() => this.loadRates());
  }

  resetForm(): void {
    this.editingId = null;
    this.form = this.empty();
  }

  validate(): boolean {
    if (this.form.minFat >= this.form.maxFat) {
      alert('Min Fat must be less than Max Fat');
      return false;
    }
    if (this.form.ratePerLitre <= 0) {
      alert('Rate must be greater than 0');
      return false;
    }
    return true;
  }
}