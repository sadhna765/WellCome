import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MilkCollectionRoutingModule } from './milk-collection-routing-module';
import { AddCollectionComponent } from './add-collection/add-collection';
import { CollectionListComponent } from './collection-list/collection-list';
import { ViewCollection } from './view-collection/view-collection';


@NgModule({
  declarations: [
    AddCollectionComponent,
    CollectionListComponent,
   ViewCollection
  ],
  imports: [
    CommonModule,
    FormsModule,
    MilkCollectionRoutingModule
  ]
})
export class MilkCollectionModule { }