
import { Routes } from '@angular/router';
import { LineListComponent } from './components/line-list/line-list.component';
import { LineAddComponent } from './components/line-add/line-add.component';
import { LineDeleteComponent } from './components/line-delete/line-delete.component';
import { LineUpdateComponent } from './components/line-update/line-update.component';
import { CallAddComponent } from './components/call-add/call-add.component';
import { CallUpdateComponent } from './components/call-update/call-update.component';
import { CallDeleteComponent } from './components/call-delete/call-delete.component';
import { CallListComponent } from './components/call-list/call-list.component';
import { ServiceAddComponent } from './components/service-add/service-add.component';
import { ServiceListComponent } from './components/service-list/service-list.component';
import { ServiceDeleteComponent } from './components/service-delete/service-delete.component';
import { ServiceUpdateComponent } from './components/service-update/service-update.component';


export const ROUTES: Routes = [
    { path: 'line/insert', component: LineAddComponent },
    { path: 'line/update', component: LineUpdateComponent },
    { path: 'line/delete', component: LineDeleteComponent },
    { path: 'line/list', component: LineListComponent },
  


    { path: 'call/insert', component: CallAddComponent },
    { path: 'call/update', component: CallUpdateComponent },
    { path: 'call/delete', component: CallDeleteComponent },
    { path: 'call/list', component: CallListComponent },
 

    
    { path: 'service/insert', component: ServiceAddComponent },
    { path: 'service/update', component:  ServiceUpdateComponent },
    { path: 'service/delete', component:  ServiceDeleteComponent },
    { path: 'service/list', component:  ServiceListComponent },
    { path: '', pathMatch: 'full', redirectTo: '/' },
    { path: '**', pathMatch: 'full', redirectTo: '/' }
];


