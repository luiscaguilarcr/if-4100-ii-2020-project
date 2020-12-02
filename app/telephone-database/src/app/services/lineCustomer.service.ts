import { Injectable } from '@angular/core';
import { LineCustomer } from '../models/line_customer.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LineCustomerService {
  url = 'http://186.176.127.9:2525/line_customer';

  constructor(private http: HttpClient) {}

  add(LineCustomer: LineCustomer): Observable<any> {
    return this.http.post(this.url, LineCustomer);
  }
  update(LineCustomer: LineCustomer): Observable<any> {
    return this.http.put(this.url, LineCustomer);
  }

  getList(): Observable<any>{
    return this.http.get(this.url);
  }
  delete(lineCustomer: LineCustomer): Observable<any>{
    return this.http.delete(this.url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json; charset=utf-8',
      })
      .append('telephoneNumber', lineCustomer.telephoneNumber.toString()),
      observe: 'response',
      responseType: 'json'
    });
  }

}