import { Injectable } from '@angular/core';
import { Line } from '../models/line.model';
import { DatabaseService } from './database.service';

@Injectable({
  providedIn: 'root'
})
export class CallService {
  variableTypes : any;

  constructor(private databaseService: DatabaseService) {this.variableTypes = databaseService.util()}

  public addLine(line: Line) {
    
    this.databaseService.getPool().then((pool: any) => {
      const insertStatement = 'INSERT INTO [Line] (Telephone_Number, Points_Quantity, Type, Status) values (?,?,?,?);'

      return pool.request()
        .input('Telephone_Number', this.variableTypes.Nvarchar(), line.telephoneNumber)
        .input('Points_Quantity', this.variableTypes.Nvarchar(), line.type)
        .input('Type', this.variableTypes.Nvarchar(), line.type)
        .input('Status', this.variableTypes.Nvarchar(), line.type)
        .query(insertStatement)
    })
    .catch ((error: any) => {
        console.log(error);
    });
    
  }
}
