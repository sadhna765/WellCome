import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddCollectionComponent } from './add-collection/add-collection';
import { CollectionListComponent } from './collection-list/collection-list';
import { ViewCollection } from './view-collection/view-collection';

const routes: Routes = [
  { path: '', redirectTo: 'list', pathMatch: 'full' },
  { path: 'add', component: AddCollectionComponent },
  { path: 'list', component: CollectionListComponent },
  { path: 'view/:id', component: ViewCollection }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MilkCollectionRoutingModule { }
