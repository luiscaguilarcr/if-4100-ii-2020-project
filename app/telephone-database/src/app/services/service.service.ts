import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Service } from '../models/service.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceService {

  url = 'http://186.176.127.9:2525/service';
  private serviceSelected: Service = new Service();
  constructor(private http: HttpClient) { }

 add(service: Service): Observable<any> {
    return this.http.post(this.url, service);
  }
  update(service: Service): Observable<any> {
    return this.http.put(this.url, service);
  }

  getList(): Observable<any>{
    return this.http.get(this.url);
  }

  getServiceSelected(): Service{
    return this.serviceSelected;
  }
  setServiceSelected(service: Service): void{
    this.serviceSelected = service;
  }

  delete(service: Service): Observable<any>{
    return this.http.delete(this.url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json; charset=utf-8',
      })
      .append('serviceCode', service.serviceCode.toString()),
      observe: 'response',
      responseType: 'json'
    });
  }
}
