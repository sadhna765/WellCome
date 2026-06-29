import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MilkCollectionService } from '../../../service/milk-collection';
import { RateService } from '../../rate-management/rate-service';
@Component({
  selector: 'app-add-collection',
  standalone: false,
  templateUrl: './add-collection.html',
  styleUrl: './add-collection.css',
})
export class AddCollectionComponent implements OnInit {

  farmers: any[] = [];
  collection: any = {
    collectionDate: new Date().toISOString().split('T')[0],
    shift: '',
    quantity: null,
    fat: null,
    snf: null,
    rate: null
  };
  selectedFarmerId: number | null = null;
  

  constructor(
    private milkCollectionService: MilkCollectionService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private rateService: RateService
  ) {}
  

fatRates: any[] = [];

ngOnInit(): void {

  this.milkCollectionService.getAllFarmers().subscribe(data => {
    this.farmers = data;
    this.cdr.detectChanges();
  });

  this.rateService.lookup(this.collection.milkType, this.collection.fat)
  .subscribe(res => {
    this.collection.rate = res.rate;
    this.calculateAmount();
  });
      this.rateService.lookup(this.collection.milkType, this.collection.fat)
  .subscribe(res => {
    this.collection.rate = res.rate;
    this.calculateAmount();
    this.rateService.lookup(this.collection.milkType, this.collection.fat)
  .subscribe(res => {
    this.collection.rate = res.rate;
    this.calculateAmount();
  });
  });
    
}

onFatChange(): void {

  const fat = Number(this.collection.fat);

  const match = this.fatRates.find(
    x => Number(x.fat) === fat
  );

  if (match) {

    this.collection.rate = match.rate;

    this.calculatedAmount =
      Number(this.collection.quantity || 0) *
      Number(this.collection.rate || 0);

  } else {

    this.collection.rate = 0;
    this.calculatedAmount = 0;
  }
}

  saveCollection(): void {
    if (!this.selectedFarmerId) {
      alert('Please select a farmer!');
      return;
    }
    if (!this.collection.collectionDate) {
      alert('Please select collection date!');
      return;
    }
    if (!this.collection.shift) {
      alert('Please select morning or evening shift!');
      return;
    }
    this.collection.amount = this.calculatedAmount;
    this.milkCollectionService.addCollection(this.selectedFarmerId, this.collection)
      .subscribe({
        next: () => {
          alert('Collection saved successfully!');
          this.router.navigate(['/milk-collection/list']);
         
        },
        error: (err: any) => {
          console.error(err);
         alert('Failed to save collection!');
        }
      });
     
  }

  cancel(): void {
    this.router.navigate(['/milk-collection/list']);
  }
milkType: string = '';
calculatedAmount: number = 0;

// rate config — apne actual rate yaha daal de
rateConfig: any = {
  COW:     { fatRate: 6.5, snfRate: 0 },
  BUFFALO: { fatRate: 7.5, snfRate: 0 }
};

calculateRate(): void {
  const cfg = this.rateConfig[this.milkType];
  if (!cfg || !this.collection.fat) {
    this.collection.rate = 0;
    this.calculateAmount();
    return;
  }
  const fat = +this.collection.fat || 0;
  const snf = +this.collection.snf || 0;
  this.collection.rate = +(fat * cfg.fatRate + snf * cfg.snfRate).toFixed(2);
  this.calculateAmount();
}

calculateAmount(): void {
  const qty  = +this.collection.quantity || 0;
  const rate = +this.collection.rate || 0;
  this.calculatedAmount = +(qty * rate).toFixed(2);
}
}