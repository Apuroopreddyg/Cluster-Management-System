import { Routes } from '@angular/router';

import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { HospitalLayoutComponent } from './pages/hospital-layout/hospital-layout.component';
import { HospitalOverviewComponent } from './pages/hospital-overview/hospital-overview.component';
import { HospitalOpComponent } from './pages/hospital-op/hospital-op.component';
import { HospitalPharmacyComponent } from './pages/hospital-pharmacy/hospital-pharmacy.component';

export const routes: Routes = [

  {
    path: '',
    component: DashboardComponent
  },

  {
    path: 'hospitals/:hospitalId',
    component: HospitalLayoutComponent,
    children: [
      {
        path: '',
        redirectTo: 'overview',
        pathMatch: 'full'
      },
      {
        path: 'overview',
        component: HospitalOverviewComponent
      },
      {
        path: 'op',
        component: HospitalOpComponent
      },
      {
        path: 'pharmacy',
        component: HospitalPharmacyComponent
      }
    ]
  },

  {
    path: '**',
    redirectTo: ''
  }
];