import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LineListComponent } from './components/line-list/line-list.component';
import { RouterModule } from '@angular/router';
import { ROUTES } from './app.routes';
import { LineAddComponent } from './components/line-add/line-add.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { LineDeleteComponent } from './components/line-delete/line-delete.component';
import { LineUpdateComponent } from './components/line-update/line-update.component';

@NgModule({
  declarations: [
    AppComponent,
    LineListComponent,
    LineAddComponent,
    NavbarComponent,
    LineDeleteComponent,
    LineUpdateComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot( ROUTES, { useHash: true} )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
