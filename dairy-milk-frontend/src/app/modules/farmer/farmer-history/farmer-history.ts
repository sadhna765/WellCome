import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MilkCollectionService } from '../../../service/milk-collection';
import { CattleFeedService } from '../../../service/cattle-feed';

@Component({
  selector: 'app-farmer-history',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './farmer-history.html',
  styleUrls: ['./farmer-history.css']
})
export class FarmerHistory implements OnInit {

  farmerId!: string;
  farmerName = '';
  numericFarmerId = 0;

  // Milk Collection
  allRecords: any[] = [];
  filteredRecords: any[] = [];
  filterType = 'all';
  selectedShift = 'ALL';
  customFrom = '';
  customTo = '';
  summary = { totalMilk: 0, totalAmount: 0 };

  // Cattle Feed
  cattleFeedRecords: any[] = [];
  cattleFeedSummary = { totalQuantity: 0, totalAmount: 0 };
  showAddFeed = false;
  newFeed = {
    feedDate: '',
    feedType: 'Mineral Mixture',
    quantity: null,
    rate: null,
    amount: 0
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private milkCollectionService: MilkCollectionService,
    private cattleFeedService: CattleFeedService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.farmerId = this.route.snapshot.paramMap.get('farmerId') || '';
    this.farmerName = this.route.snapshot.queryParamMap.get('name') || '';
    this.numericFarmerId = parseInt(this.farmerId.replace(/\D/g, ''), 10);
    this.loadHistory();
    this.loadCattleFeed();
  }

  // ══════════════════════════════
  // MILK COLLECTION
  // ══════════════════════════════

  loadHistory(): void {
    this.milkCollectionService.getAllMergedCollections().subscribe({
      next: (data) => {
        const numericId = parseInt(this.farmerId.replace(/\D/g, ''), 10);
        this.allRecords = data.filter((c: any) => {
          if (String(c.farmerId) === String(this.farmerId)) return true;
          if (Number(c.farmerId) === numericId) return true;
          if (c.farmerName === this.farmerName) return true;
          return false;
        });
        this.applyFilter();
        this.cdr.detectChanges();
      },
      error: (err: any) => console.error('History load error:', err)
    });
  }

  applyFilter(): void {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    let records = [...this.allRecords];

    if (this.filterType === 'today') {
      records = records.filter(c => this.isSameDay(c.collectionDate, today));
    } else if (this.filterType === 'yesterday') {
      const yesterday = new Date(today);
      yesterday.setDate(today.getDate() - 1);
      records = records.filter(c => this.isSameDay(c.collectionDate, yesterday));
    } else if (this.filterType === 'week') {
      const weekAgo = new Date(today);
      weekAgo.setDate(today.getDate() - 7);
      records = records.filter(c => new Date(c.collectionDate) >= weekAgo);
    } else if (this.filterType === 'year') {
      records = records.filter(c =>
        new Date(c.collectionDate).getFullYear() === today.getFullYear()
      );
    } else if (this.filterType === 'custom') {
      if (this.customFrom && this.customTo) {
        const from = new Date(this.customFrom);
        const to = new Date(this.customTo);
        to.setHours(23, 59, 59);
        records = records.filter(c => {
          const d = new Date(c.collectionDate);
          return d >= from && d <= to;
        });
      }
    }

    if (this.selectedShift !== 'ALL') {
      records = records.filter(c => c.shift === this.selectedShift);
    }

    records.sort((a, b) =>
      new Date(a.collectionDate).getTime() - new Date(b.collectionDate).getTime()
    );

    this.filteredRecords = records;
    this.updateSummary(records);
  }

  private isSameDay(dateStr: string, compare: Date): boolean {
    const d = new Date(dateStr);
    return (
      d.getFullYear() === compare.getFullYear() &&
      d.getMonth() === compare.getMonth() &&
      d.getDate() === compare.getDate()
    );
  }

  private updateSummary(records: any[]): void {
    this.summary = records.reduce(
      (total, c) => ({
        totalMilk: total.totalMilk + Number(c.shift === 'MORNING' ? c.morningQuantity : c.eveningQuantity) || 0,
        totalAmount: total.totalAmount + Number(c.shift === 'MORNING' ? c.morningAmount : c.eveningAmount) || 0
      }),
      { totalMilk: 0, totalAmount: 0 }
    );
  }

  getQty(c: any): number { return c.shift === 'MORNING' ? c.morningQuantity : c.eveningQuantity; }
  getFat(c: any): number { return c.shift === 'MORNING' ? c.morningFat : c.eveningFat; }
  getSnf(c: any): number { return c.shift === 'MORNING' ? c.morningSnf : c.eveningSnf; }
  getRate(c: any): number { return c.shift === 'MORNING' ? c.morningRate : c.eveningRate; }
  getAmount(c: any): number { return c.shift === 'MORNING' ? c.morningAmount : c.eveningAmount; }

  // ══════════════════════════════
  // CATTLE FEED
  // ══════════════════════════════

  loadCattleFeed(): void {
    this.cattleFeedService.getByFarmer(this.numericFarmerId).subscribe({
      next: (data) => {
        this.cattleFeedRecords = data.sort((a: any, b: any) =>
          new Date(a.feedDate).getTime() - new Date(b.feedDate).getTime()
        );
        this.updateFeedSummary(data);
        this.cdr.detectChanges();
      },
      error: (err: any) => console.error('Cattle feed load error:', err)
    });
  }

  calculateAmount(): void {
    if (this.newFeed.quantity && this.newFeed.rate) {
      this.newFeed.amount = Math.round(this.newFeed.quantity * this.newFeed.rate * 100) / 100;
    }
  }

  saveFeed(): void {
    if (!this.newFeed.feedDate || !this.newFeed.quantity || !this.newFeed.rate) {
      alert('Date, Quantity aur Rate required hai!');
      return;
    }

    this.cattleFeedService.save(this.numericFarmerId, this.newFeed).subscribe({
      next: () => {
        this.showAddFeed = false;
        this.newFeed = { feedDate: '', feedType: 'Mineral Mixture', quantity: null, rate: null, amount: 0 };
        this.loadCattleFeed();
      },
      error: (err: any) => console.error('Save error:', err)
    });
  }

  deleteFeed(id: number): void {
    if (confirm('Delete karna chahte ho?')) {
      this.cattleFeedService.delete(id).subscribe({
        next: () => this.loadCattleFeed(),
        error: (err: any) => console.error('Delete error:', err)
      });
    }
  }

  private updateFeedSummary(records: any[]): void {
    this.cattleFeedSummary = records.reduce(
      (total, c) => ({
        totalQuantity: total.totalQuantity + Number(c.quantity || 0),
        totalAmount: total.totalAmount + Number(c.amount || 0)
      }),
      { totalQuantity: 0, totalAmount: 0 }
    );
  }

  goBack(): void {
    this.router.navigate(['/farmers']);
  }
}