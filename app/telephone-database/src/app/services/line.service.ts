import { Injectable } from '@angular/core';
import { Line } from '../models/line.model';
import { DataService } from './data.service';

@Injectable({
  providedIn: 'root'
})
export class LineService {

  constructor(private dataService: DataService) {}

  public addLine(line: Line) {

  }

  public getLine(): Line{
    return new Line();
  }


}
