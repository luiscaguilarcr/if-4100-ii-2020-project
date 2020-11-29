import { Injectable } from '@angular/core';
import { __await } from 'tslib';

@Injectable({
  providedIn: 'root'
})
export class DatabaseService {

  config = {
    user: 'laboratorios',
    password: 'KmZpo.2796',
    server: '163.178.107.10',
    database: 'B88782_B90127_B90514',
  }
  pool;
  sql = require('mssql');

  constructor() {
  }


  executeQuery(query: string){
    this.pool = this.sql.connect(this.config).then({

    })
    .catch({
      
    });
  }
}
