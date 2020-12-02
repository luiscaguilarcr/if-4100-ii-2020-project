
import { Routes } from '@angular/router';

/* Components */
import { LineListComponent } from './components/line-list/line-list.component';
import { LineAddComponent } from './components/line-add/line-add.component';
import { LineUpdateComponent } from './components/line-update/line-update.component';
import { LineCustomerAddComponent } from './components/line-customer-add/line-customer-add.component';
import { LineCustomerListComponent } from './components/line-customer-list/line-customer-list.component';
import { CallListComponent } from './components/call-list/call-list.component';
import { ServiceAddComponent } from './components/service-add/service-add.component';
import { ServiceListComponent } from './components/service-list/service-list.component';
import { ServiceUpdateComponent } from './components/service-update/service-update.component';

/* Rutes */
export const ROUTES: Routes = [
    { path: 'line/insert', component: LineAddComponent },
    { path: 'line/update', component: LineUpdateComponent },
    { path: 'line/list', component: LineListComponent },
    { path: 'lineCustomer/add', component: LineCustomerAddComponent },
    { path: 'lineCustomer/list', component: LineCustomerListComponent },
    { path: 'call/list', component: CallListComponent },
    { path: 'service/insert', component: ServiceAddComponent },
    { path: 'service/update', component:  ServiceUpdateComponent },
    { path: 'service/list', component:  ServiceListComponent },
    { path: '', pathMatch: 'full', redirectTo: '/' },
    { path: '**', pathMatch: 'full', redirectTo: '/' }
];


