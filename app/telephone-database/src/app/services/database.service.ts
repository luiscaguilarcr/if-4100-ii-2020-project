import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DatabaseService {

  config = {
    user: 'laboratorios',
    password: 'KmZpo.2796',
    server: '163.178.107.10',
    database: 'B88782_B90127_B90514',
  };

  sql = require('mssql');

  constructor() {
  }


  getPool(): any {
    return this.sql.connect(this.config);
  }
}
