import { Injectable } from '@angular/core';
import { DatabaseService } from './database.service';
import { Service } from '../models/service.model';

@Injectable({
  providedIn: 'root',
})
export class ServiceService {
  variableTypes: any;
  constructor(private databaseService: DatabaseService) {
    this.variableTypes = databaseService.util();
  }

  add(service: Service): any {
    return this.databaseService
      .getPool()
      .then((pool: any) => {
        const query =
          'INSERT INTO [Service] (Name, Description, Cost) values (@Name, @Description, @Cost);';

        return pool
          .request()
          .input('Name', this.variableTypes.Int, service.name)
          .input('Description', this.variableTypes.Int, service.description)
          .input('Cost', this.variableTypes.DateTime, service.cost)
          .query(query);
      })
      .catch((error: any) => {
        console.log(error);
      });
  }
  update(service: Service): any {
    return this.databaseService
      .getPool()
      .then((pool: any) => {
        const query =
        'UPDATE [Service] SET [Name] = @Name, [Description] = @Description, [Cost] = @Cost, [Status] = @Status WHERE [Service_Code] = @Service_Code';
        return pool
          .request()
          .input('Name', this.variableTypes.Nvarchar(50), service.name)
          .input('Description', this.variableTypes.Nvarchar(200), service.description)
          .input('Cost', this.variableTypes.Money, service.cost)
          .input('Status', this.variableTypes.Char(1), service.status)
          .input('Service_Code', this.variableTypes.Int, service.serviceCode)
          .query(query);
      })
      .catch((error: any) => {
        console.log(error);
      });
  }
  delete(service: Service): any {
    return this.databaseService
      .getPool()
      .then((pool: any) => {
        const query =
          'DELETE [Service] WHERE [Service_Code] = @Service_Code';
        return pool
          .request()
          .input('Service_Code', this.variableTypes.Int, service.serviceCode)
          .query(query);
      })
      .catch((error: any) => {
        console.log(error);
      });
  }

  get(service: Service): any {
    return this.databaseService
      .getPool()
      .then((pool: any) => {
        const query =
          'SELECT [Name], [Description], [Cost], [Status] FROM [Service] WHERE [Service_Code] = @Service_Code';
        return pool.request()
          .input('Service_Code', this.variableTypes.Int, service.serviceCode)
          .query(query);
      })
      .catch((error: any) => {
        console.log(error);
      });
  }
}
