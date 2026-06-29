import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MilkCollectionService } from '../../../service/milk-collection';

@Component({
  selector: 'app-collection-list',
  standalone: false,
  templateUrl: './collection-list.html',
  styleUrls: ['./collection-list.css']
})

export class CollectionListComponent implements OnInit {

  collections: any[] = [];
  summary = {
    totalMilk: 0,
    totalAmount: 0
  };
  selectedDate = '';
  
collection = {
  quantity: 0,
  fat: 0,
  rate: 0,
  amount: 0
};

fatRates = [
  { fat: 3.5, rate: 40 },
  { fat: 4.0, rate: 45 }
];

  constructor(
    private milkCollectionService: MilkCollectionService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadCollections();
  }

  loadCollections(): void {
    const request = this.selectedDate
      ? this.milkCollectionService.getMergedByDate(this.selectedDate)
      : this.milkCollectionService.getAllMergedCollections();

    request.subscribe({
      next: (data) => {
        this.collections = data;
        this.updateSummary(data);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Collection list not loaded:', err);
        this.collections = [];
        this.updateSummary([]);
      }
    });
  }

  addCollection(): void {
   this.router.navigate(['/milk-collection/add']);
  }

  clearDate(): void {
    this.selectedDate = '';
    this.loadCollections();
  }

  private updateSummary(collections: any[]): void {
    this.summary = collections.reduce(
      (total, collection) => ({
        totalMilk: total.totalMilk + Number(collection.totalQuantity || 0),
        totalAmount: total.totalAmount + Number(collection.totalAmount || 0)
      }),
      { totalMilk: 0, totalAmount: 0 }
    );
  }
  onFatChange() {

  const fat = Number(this.collection.fat);

  const match = this.fatRates.find(
    x => Number(x.fat) === fat
  );

  if (match) {

    this.collection.rate = match.rate;

    this.collection.amount =
      this.collection.quantity *
      this.collection.rate;
  }
}
}
