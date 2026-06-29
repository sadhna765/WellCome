import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Farmer } from '../../../service/farmer';

@Component({
  selector: 'app-farmer-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './farmer-list.html',
  styleUrls: ['./farmer-list.css']
})
export class FarmerList implements OnInit {

  searchText = '';
  statusFilter = 'All';

  farmers: any[] = [];

  get filteredFarmers(): any[] {
    const search = this.searchText.trim().toLowerCase();

    return this.farmers.filter((farmer) => {
      const status = String(farmer.status ?? '').toUpperCase();
      const matchesStatus = this.statusFilter === 'All' || status === this.statusFilter;
      console.log('matchesStatus',matchesStatus);
      const matchesSearch = !search || [
        farmer.farmerId,
        farmer.name,
        farmer.mobile
      ].some((value) => String(value ?? '').toLowerCase().includes(search));

      return matchesStatus && matchesSearch;
    });
  }

  constructor(
    private router: Router,
  private farmerService: Farmer,
  private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadFarmers();
  }

  loadFarmers() {
  this.farmerService.getFarmers().subscribe({
    next: (data) => {
      this.farmers = data.map((farmer) => ({
        ...farmer,
        status: String(farmer.status ?? 'ACTIVE').toUpperCase()
      }));
       this.statusFilter = 'All';
  this.cdr.detectChanges();
        console.log('Farmers:', this.farmers);
    },
    error: (err) => {
      console.error(err);
    }
  });
}

addFarmer() {
  this.router.navigate(['/farmers/add']);
}
addCollection() {
 // alert('Add Collection clicked');
  this.router.navigate(['/milk-collection/add']);
}

viewFarmer(farmerId: string) {
  this.router.navigate(['/farmers/view', farmerId]);
}
viewHistory(farmerId: string, farmerName: string) {
  this.router.navigate(['/farmers/history', farmerId], {
    queryParams: { name: farmerName }
  });
}
editFarmer(farmerId: string) {
  this.router.navigate(['/farmers/edit', farmerId]);
}

toggleStatus(farmer: any) {
  farmer.status = farmer.status === 'ACTIVE'
    ? 'INACTIVE'
    : 'ACTIVE';
}
}
