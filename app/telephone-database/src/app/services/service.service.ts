import { Injectable } from '@angular/core';
import { Service } from '../models/service.model';
import { DataService } from './data.service';

@Injectable({
  providedIn: 'root',
})
export class ServiceService {
  constructor(private dataService: DataService) { }

  add(service: Service): any {
    
  }
  update(service: Service): any {
    
  }
  delete(service: Service): any {
    
  }

  get(service: Service): any {
   
  }
}
