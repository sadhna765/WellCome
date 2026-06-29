import { Routes } from '@angular/router';
import { PaymentManagement } from './modules/create-component/payment-management';

  import { ViewScheme } from './modules/scheme-management/view-scheme/view-scheme';
import { AddScheme } from './modules/scheme-management/add-scheme/add-scheme';


export const routes: Routes = [
  {
    path: 'farmers',
    loadComponent: () =>
      import('./modules/farmer/farmer-list/farmer-list')
        .then(m => m.FarmerList)
  },
  {
    path: 'farmers/add',
    loadComponent: () =>
      import('./modules/farmer/add-farmer/add-farmer')
        .then(m => m.AddFarmer)
  },
  {
    path: 'farmers/history/:farmerId',
    loadComponent: () =>
      import('./modules/farmer/farmer-history/farmer-history')
        .then(m => m.FarmerHistory)
  },
  {
    path: 'farmers/edit/:id',
    loadComponent: () =>
      import('./modules/farmer/add-farmer/add-farmer')
        .then(m => m.AddFarmer)
  },
  {
    path: 'farmers/view/:id',
    loadComponent: () =>
      import('./modules/farmer/view-farmer/view-farmer')
        .then(m => m.ViewFarmer)
  },
  {
    path: 'milk-collection',
    loadChildren: () =>
      import('./modules/milk-collection/milk-collection.module')
        .then(m => m.MilkCollectionModule)
  },
  {
    path: 'milk-collection/view/:id',
    loadComponent: () =>
      import('./modules/milk-collection/view-collection/view-collection')
        .then(m => m.ViewCollection)
  },

  // Payment Module
  {
    path: 'payments',
    component: PaymentManagement
  },
{
  path: 'rate-management',
  loadComponent: () =>
    import('./modules/rate-management/rate-management')
      .then(m => m.RateManagement)
},
  {
    path: '',
    redirectTo: 'farmers',
    pathMatch: 'full'
  }
,

// routes array ke andar:
{ path: 'schemes', component: ViewScheme },
{ path: 'schemes/add', component: AddScheme },
{ path: 'schemes/edit/:id', component: AddScheme },
{
  path: 'dashboard',
  loadComponent: () =>
    import('./modules/dashboard/dashboard')
      .then(m => m.DashboardComponent)
},
{
  path: 'advances',
  loadComponent: () => import('./modules/advances/advances').then(m => m.AdvancesComponent)

},
{
  path: 'users',
  loadComponent: () => import('./modules/users/users').then(m => m.UsersComponent)
},
{
  path: 'settings',
  loadComponent: () => import('./modules/settings/settings').then(m => m.SettingsComponent)
},

];