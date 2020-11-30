import { Injectable } from '@angular/core';
import { DatabaseService } from './database.service';
import { Service } from '../models/service.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  variableTypes : any;

  constructor(private databaseService: DatabaseService) {this.variableTypes = databaseService.util()}

  public addCall(service: Service) {
    
    this.databaseService.getPool().then((pool: any) => {
      const insertStatement = 'INSERT INTO [Line] (Service_Code, Name, Description, Cost) values (?,?,?,?);'

      return pool.request()
        .input('Service_Code', this.variableTypes.Int(), service.serviceCode)
        .input('Name', this.variableTypes.Int(), service.name)
        .input('Description', this.variableTypes.Int(), service.description)
        .input('Cost', this.variableTypes.DateTime(), service.cost)
        .query(insertStatement)
    })
    .catch ((error: any) => {
        console.log(error);
    });
    
  }


  
}
