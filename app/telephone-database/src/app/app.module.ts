import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

/* Services */
import { RouterModule } from '@angular/router';
import { ROUTES } from './app.routes';
import { HttpClientModule } from '@angular/common/http';
/* Components */
import { AppComponent } from './app.component';
import { LineListComponent } from './components/line-list/line-list.component';
import { LineAddComponent } from './components/line-add/line-add.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { LineCustomerAddComponent } from './components/line-customer-add/line-customer-add.component';
import { LineCustomerListComponent } from './components/line-customer-list/line-customer-list.component';

import { ReactiveFormsModule } from '@angular/forms';
import { ServiceAddComponent } from './components/service-add/service-add.component';
import { ServiceListComponent } from './components/service-list/service-list.component';
import { ServiceUpdateComponent } from './components/service-update/service-update.component';
import { CallAddComponent } from './components/call-add/call-add.component';
import { CallDeleteComponent } from './components/call-delete/call-delete.component';
import { CallUpdateComponent } from './components/call-update/call-update.component';
import { CallListComponent } from './components/call-list/call-list.component';
import { LineUpdateComponent } from './components/line-update/line-update.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { LineCodePipe } from './line-code.pipe';

@NgModule({
  declarations: [
    AppComponent,
    LineListComponent,
    LineAddComponent,
    LineCustomerAddComponent,
    LineCustomerListComponent,
    NavbarComponent,
    ServiceAddComponent,
    ServiceListComponent,
    ServiceUpdateComponent,
    CallAddComponent,
    CallDeleteComponent,
    CallUpdateComponent,
    CallListComponent,
    LineUpdateComponent,
    LineCodePipe
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule,
    RouterModule.forRoot( ROUTES, { useHash: true} )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
