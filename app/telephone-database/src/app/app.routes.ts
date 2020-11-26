
import { Routes } from '@angular/router';
import { LineListComponent } from './components/line-list/line-list.component';
import { LineAddComponent } from './components/line-add/line-add.component';
import { LineDeleteComponent } from './components/line-delete/line-delete.component';
import { LineUpdateComponent } from './components/line-update/line-update.component';


export const ROUTES: Routes = [
    { path: 'line/insert', component: LineAddComponent },
    { path: 'line/update', component: LineUpdateComponent },
    { path: 'line/delete', component: LineDeleteComponent },
    { path: 'line/list', component: LineListComponent },
    { path: '', pathMatch: 'full', redirectTo: '/' },
    { path: '**', pathMatch: 'full', redirectTo: '/' }
];
