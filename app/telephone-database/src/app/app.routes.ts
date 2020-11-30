
import { Routes } from '@angular/router';

/* Components */
import { LineListComponent } from './components/line-list/line-list.component';
import { LineAddComponent } from './components/line-add/line-add.component';
import { LineDeleteComponent } from './components/line-delete/line-delete.component';
import { LineUpdateComponent } from './components/line-update/line-update.component';
import { LineCustomerAddComponent } from './components/line-customer-add/line-customer-add.component';

import { CallAddComponent } from './components/call-add/call-add.component';
import { CallUpdateComponent } from './components/call-update/call-update.component';
import { CallDeleteComponent } from './components/call-delete/call-delete.component';
import { CallListComponent } from './components/call-list/call-list.component';
import { ServiceAddComponent } from './components/service-add/service-add.component';
import { ServiceListComponent } from './components/service-list/service-list.component';
import { ServiceUpdateComponent } from './components/service-update/service-update.component';

/* Rutes */
export const ROUTES: Routes = [
    { path: 'line/insert', component: LineAddComponent },
    { path: 'line/update', component: LineUpdateComponent },
    { path: 'line/delete', component: LineDeleteComponent },
    { path: 'line/list', component: LineListComponent },
    
    { path: 'lineCustomer/add', component: LineCustomerAddComponent },

    { path: 'call/insert', component: CallAddComponent },
    { path: 'call/update', component: CallUpdateComponent },
    { path: 'call/delete', component: CallDeleteComponent },
    { path: 'call/list', component: CallListComponent },
    { path: 'service/insert', component: ServiceAddComponent },
    { path: 'service/update', component:  ServiceUpdateComponent },
    { path: 'service/list', component:  ServiceListComponent },
    { path: '', pathMatch: 'full', redirectTo: '/' },
    { path: '**', pathMatch: 'full', redirectTo: '/' }
];


