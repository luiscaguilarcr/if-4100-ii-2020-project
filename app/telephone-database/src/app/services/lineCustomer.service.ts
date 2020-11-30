import { Injectable } from '@angular/core';
import { LineCustomer } from '../models/line_customer.model';
import { DatabaseService } from './database.service';

@Injectable({
  providedIn: 'root'
})
export class LineCustomerService {
  variableTypes : any;

  constructor(private databaseService: DatabaseService) {this.variableTypes = databaseService.util()}

  public addLine(lineCustomer: LineCustomer) {
    
    return this.databaseService.getPool().then((pool: any) => {
      const insertStatement = 'INSERT INTO [Line_Customer] (Telephone_Number, Customer_Id, Customer_First_Name, Customer_Last_Name, Customer_Address, Customer_Email) values (@Telephone_Number, @Customer_Id, @Customer_First_Name, @Customer_LastName_Name, @Customer_Address, @Customer_Email);'

      return pool.request()
        .input('Telephone_Number', this.variableTypes.Int, lineCustomer.telephoneNumber)
        .input('Customer_Id', this.variableTypes.Int, lineCustomer.customerId)
        .input('Customer_First_Name', this.variableTypes.tinyint, lineCustomer.customerFirstName)
        .input('Customer_Last_Name', this.variableTypes.tinyint, lineCustomer.customerLastName)
        .input('Customer_Address', this.variableTypes.tinyint, lineCustomer.customerAddress)
        .input('Customer_Email', this.variableTypes.tinyint, lineCustomer.customerEmail)

        .query(insertStatement)
    })
    .catch ((error: any) => {
        console.log(error);
    });
    
  }

}