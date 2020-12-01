import { Injectable } from '@angular/core';
import { Call } from '../models/call.model';
import { DataService } from './data.service';

@Injectable({
  providedIn: 'root'
})
export class CallService {

  constructor(private dataService: DataService) {

  }

  public addCall(call: Call) {  }
}
