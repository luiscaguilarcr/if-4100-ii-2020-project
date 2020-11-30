import { Injectable } from '@angular/core';
import { Call } from '../models/call.model';
import { DatabaseService } from './database.service';

@Injectable({
  providedIn: 'root'
})
export class CallService {
  variableTypes : any;

  constructor(private databaseService: DatabaseService) {this.variableTypes = databaseService.util()}

  public addCall(call: Call) {
    
    this.databaseService.getPool().then((pool: any) => {
      const insertStatement = 'INSERT INTO [Line] (Telephone_Number, Points_Quantity, Type, Status) values (?,?,?,?);'

      return pool.request()
        .input('Telephone_Number', this.variableTypes.Nvarchar(), call.telephoneNumber)
        .input('Points_Quantity', this.variableTypes.Nvarchar(), call.type)
        .input('Type', this.variableTypes.Nvarchar(), call.type)
        .input('Status', this.variableTypes.Nvarchar(), call.type)
        .query(insertStatement)
    })
    .catch ((error: any) => {
        console.log(error);
    });
    
  }
}
