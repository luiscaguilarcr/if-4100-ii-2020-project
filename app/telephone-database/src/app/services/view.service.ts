import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { View } from '../models/view.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ViewService {
  url = 'http://186.176.127.9:2525/view';
  private viewSelected: View = new View();
  constructor(private http: HttpClient) {}

  add(view: View): Observable<any> {
    return this.http.post(this.url, JSON.stringify(view));
  }
  update(view: View): Observable<any> {
    return this.http.put(this.url, view);
  }

  getList(): Observable<any>{
    return this.http.get(this.url);
  }

  getViewSelected(): View{
    return this.viewSelected;
  }
  setViewSelected(view: View): void{
    this.viewSelected = view;
  }

  delete(view: View): Observable<any>{
      return this.http.delete(this.url, {
        headers: new HttpHeaders({
        'Content-Type': 'application/json; charset=utf-8',
      })
      .append('telephoneNumber', view.telephoneNumber.toString()),
      observe: 'response',
      responseType: 'json'
    });
  }

}
