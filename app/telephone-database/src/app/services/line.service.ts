import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Line } from '../models/line.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LineService {
  url = 'http://186.176.127.9:2525/line';

  constructor(private http: HttpClient) {}

  add(line: Line): Observable<any> {
    return this.http.post(this.url, line);
  }
  update(line: Line): Observable<any> {
    return this.http.put(this.url, line);
  }

  getList(): Observable<any>{
    return this.http.get(this.url);
  }
  delete(line: Line): Observable<any>{
    return this.http.delete(this.url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json; charset=utf-8',
      })
      .append('telephoneNumber', line.telephoneNumber.toString()),
      observe: 'response',
      responseType: 'json'
    });
  }

}
