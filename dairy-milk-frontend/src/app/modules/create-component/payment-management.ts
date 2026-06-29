import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServiceManagement } from '../../service/service-management';

@Component({
  selector: 'app-payment-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './payment-management.html',
  styleUrls: ['./payment-management.css']
})
export class PaymentManagement implements OnInit {

  payments: any[] = [];

  payment = {
    farmerId: '',
    farmerName: '',
    fromDate: '',
    toDate: '',
    milkAmount: 0,
    feedDeduction: 0,
    advanceDeduction: 0,
    netAmount: 0,
    paymentDate: '',
    status: 'PENDING',
    paymentCycle: 'MONTHLY'
  };
constructor(private paymentService: ServiceManagement) {}

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments(): void {
    this.paymentService.getAll().subscribe({
      next: (data: any) => {
        this.payments = data;
      },
      error: (err: any) => console.error(err)
    });
  }

  calculateNetAmount(): void {
    this.payment.netAmount =
      Number(this.payment.milkAmount)
      - Number(this.payment.feedDeduction)
      - Number(this.payment.advanceDeduction);
  }

  savePayment(): void {
    this.paymentService.save(this.payment).subscribe({
      next: () => {
        alert('Payment Saved Successfully');

        this.payment = {
          farmerId: '',
          farmerName: '',
          fromDate: '',
          toDate: '',
          milkAmount: 0,
          feedDeduction: 0,
          advanceDeduction: 0,
          netAmount: 0,
          paymentDate: '',
          status: 'PENDING',
          paymentCycle: 'MONTHLY'
        };

        this.loadPayments();
      },
      error: (err: any) => console.error(err)
    });
  }
}