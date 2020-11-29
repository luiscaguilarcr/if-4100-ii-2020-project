import { Injectable } from '@angular/core';
import { Injectable } from '../models/line.model';

@Injectable({
  providedIn: 'root'
})
export class LineService {

  constructor() { }

  public addLine(Line line): boolean {
    const  insertStatement = "INSERT INTO [Line] (telephoneNumber, customerId, customerFirstName, customerLastName, customerAddress, customerEmail, type) values (?,?,?,?,?,?,?);";
    const ps = new sql.PreparedStatement(db);
      
  }
}
