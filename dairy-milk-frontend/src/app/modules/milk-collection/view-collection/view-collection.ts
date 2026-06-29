import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MilkCollectionService } from '../../../service/milk-collection';

@Component({
  selector: 'app-view-collection',
  standalone: false,
  templateUrl: './view-collection.html',
  styleUrls: ['./view-collection.css']
})
export class ViewCollection implements OnInit {

  collection: any;
  loading = false;
  errorMsg = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private milkCollectionService: MilkCollectionService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    console.log('Collection ID from route:', id);
    if (!id) {
      this.errorMsg = 'No collection id provided.';
      return;
    }

    this.loading = true;
    this.milkCollectionService.getById(+id).subscribe({
      next: (data) => {
        this.collection = this.mapCollection(data);
        this.loading = false;
        console.log('Collection loaded:', this.collection);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Failed to load collection.';
        this.loading = false;
        console.error('Error loading collection:', this.errorMsg);
        this.cdr.detectChanges();
      }
    });
  }

  // Backend (MilkCollection entity) -> template view-model
  private mapCollection(data: any): any {
    return {
      id: data.id,
      farmerName: data.farmer?.name,
      farmerCode: data.farmer?.farmerId,
      shift: data.shift,
      collectionDate: data.collectionDate,
      quantity: data.quantity,
      fatPercentage: data.fat,
      snfPercentage: data.snf,
      ratePerLitre: data.rate,
      baseAmount: data.amount,
      totalAmount: data.amount
    };
  }

  goToEdit(): void {
    this.router.navigate(['/milk-collection/edit', this.collection?.id]);
  }

  goBack(): void {
    this.router.navigate(['/milk-collection/list']);
  }
}
