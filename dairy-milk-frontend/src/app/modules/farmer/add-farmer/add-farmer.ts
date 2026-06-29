import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Farmer } from '../../../service/farmer';

@Component({
  selector: 'app-add-farmer',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-farmer.html',
  styleUrl: './add-farmer.css',
})
export class AddFarmer implements OnInit {
  isEditMode = false;
  editFarmerId = '';

  farmerData: any = {
    id: '',
    farmerId: '',
    name: '',
    mobile: '',
    village: '',
    address: '',
    joiningDate: '',
    aadhaar: '',
    status: 'ACTIVE'
  };

  constructor(
    private farmerService: Farmer,
  private router: Router,
  private route: ActivatedRoute,
   private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const routeId = this.route.snapshot.paramMap.get('id');

    if (routeId) {
      this.isEditMode = true;
      this.editFarmerId = routeId;
      this.loadFarmer(routeId);
    }
  }

  loadFarmer(routeId: string) {
    this.farmerService.getFarmers().subscribe({
      next: (farmers) => {
        const farmer = farmers.find((item) =>
          String(item.farmerId) === routeId || String(item.id) === routeId
        );

        if (!farmer) {
          alert('Farmer not found');
          this.router.navigate(['/farmers']);
          return;
        }

        this.farmerData = {
          id: farmer.id ?? '',
          farmerId: farmer.farmerId ?? '',
          name: farmer.name ?? '',
          mobile: farmer.mobile ?? '',
          village: farmer.village ?? '',
          address: farmer.address ?? '',
          joiningDate: this.formatDateForInput(farmer.joiningDate),
          aadhaar: farmer.aadhaar ?? '',
          status: String(farmer.status ?? 'ACTIVE').toUpperCase()
        };
         this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Load Farmer Error:', err);
        alert('Farmer data not loaded');
      }
    });
  }

  saveFarmer() {
    console.log('Button Clicked', this.farmerData);

    if (!this.farmerData.joiningDate) {
      alert('Please select joining date');
      return;
    }

    const request = this.isEditMode
      ? this.farmerService.updateFarmer(this.farmerData.id || this.editFarmerId, this.farmerData)
      : this.farmerService.addFarmer(this.farmerData);

    request.subscribe({
      next: (res) => {
        console.log('Farmer Saved:', res);
        alert(this.isEditMode ? 'Farmer Updated Successfully' : 'Farmer Added Successfully');
        this.router.navigate(['/farmers']);
      },
      error: (err) => {
        console.error('Save Error:', err);
        alert(this.isEditMode ? 'Farmer Not Updated' : 'Farmer Not Added');
      }
    });
  }

  cancel() {
    this.router.navigate(['/farmers']);
  }

  private formatDateForInput(value: string): string {
    return value ? value.slice(0, 10) : '';
  }
}
