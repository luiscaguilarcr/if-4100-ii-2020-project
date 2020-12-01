import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Call } from '../models/call.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})


export class CallService {
  url = 'http://localhost:2525/call';
  constructor(private http: HttpClient) {}

  add(call: Call): Observable<any> {
    return this.http.post(this.url, call);
  }
  update(call: Call): Observable<any> {
    return this.http.put(this.url, call);
  }

  getList(): Observable<any>{
    return this.http.get(this.url);
  }
  
  
  delete(call: Call): Observable<any>{
    return this.http.delete(this.url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json; charset=utf-8',
      })
      .append('noCall', call.noCall.toString()),
      observe: 'response',
      responseType: 'json'
    });
  }


  }

 