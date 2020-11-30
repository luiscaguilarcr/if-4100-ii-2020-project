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
      const insertStatement = 'INSERT INTO [Line] (No_Call, Telephone_Number, Destination_Telephone_Number, Start_Date, End_Date) values (?,?,?,?,?);'

      return pool.request()
        .input('No_Call', this.variableTypes.Int(), call.noCall)
        .input('Telephone_Number', this.variableTypes.Int(), call.telephoneNumber)
        .input('Destination_Telephone_Number', this.variableTypes.Int(), call.destinationTelephoneNumber)
        .input('Start_Date', this.variableTypes.DateTime(), call.startDate)
        .input('End_Date', this.variableTypes.DateTime(), call.endDate)
        .query(insertStatement)
    })
    .catch ((error: any) => {
        console.log(error);
    });
    
  }
}