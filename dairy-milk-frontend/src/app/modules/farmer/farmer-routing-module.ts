import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FarmerList } from './farmer-list/farmer-list';
import { AddFarmer } from './add-farmer/add-farmer';
import { ViewFarmer } from './view-farmer/view-farmer';

const routes: Routes = [
  { path: '', component: FarmerList },
  { path: 'add', component: AddFarmer },
  { path: 'edit/:id', component: AddFarmer },
  { path: 'view/:id', component: ViewFarmer }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FarmerRoutingModule {}
