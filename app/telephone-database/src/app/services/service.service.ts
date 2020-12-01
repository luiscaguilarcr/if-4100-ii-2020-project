import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Service } from '../models/service.model';

@Injectable({
  providedIn: 'root',
})
export class ServiceService {
  endpoint = 'http://localhost:2525/service';
  constructor(private http: HttpClient) { }

  add(service: Service): any {
    
  }
  update(service: Service): any {
    
  }
  delete(service: Service): any {
    
  }

  get(service: Service): any {
   
  }
}
