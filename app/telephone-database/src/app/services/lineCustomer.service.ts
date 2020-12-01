import { Injectable } from '@angular/core';
import { LineCustomer } from '../models/line_customer.model';
import { DataService } from './data.service';

@Injectable({
  providedIn: 'root'
})
export class LineCustomerService {
  // endpoint = 'https://localhost:2525/line_customer';
  url = 'http://186.176.127.9:2525/line_customer';

  constructor(private DataService: DataService) { }

  public addLine(lineCustomer: LineCustomer) {

  }

}