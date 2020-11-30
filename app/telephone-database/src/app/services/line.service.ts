import { Injectable } from '@angular/core';
import { Line } from '../models/line.model';
import { DatabaseService } from './database.service';

@Injectable({
  providedIn: 'root'
})
export class LineService {
  variableTypes : any;

  constructor(private databaseService: DatabaseService) {this.variableTypes = databaseService.util()}

  public addLine(line: Line) {
    
    this.databaseService.getPool().then((pool: any) => {
      const insertStatement = 'INSERT INTO [Line] (Telephone_Number, Points_Quantity, Type) values (?,?,?);'

      return pool.request()
        .input('Telephone_Number', this.variableTypes.Int(), line.telephoneNumber)
        .input('Points_Quantity', this.variableTypes.Int(), line.pointsQuantity)
        .input('Type', this.variableTypes.tinyint(), line.type)
        .query(insertStatement)
    })
    .catch ((error: any) => {
        console.log(error);
    });
    
  }

  public getLine(): Line{
    this.databaseService.getPool().then((pool: any) => {
      const request = new this.variableTypes.Request()
      request.query(request.template 'SELECT * FROM mytable'
          // ... error checks
          console.dir(result)
      
    })
    .catch ((error: any) => {
        console.log(error);
    });
  }


}
