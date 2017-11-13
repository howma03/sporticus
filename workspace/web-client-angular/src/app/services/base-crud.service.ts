import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "./list";

@Injectable()
export class BaseCrudService<Type> {

  constructor(private http: HttpClient) {
  }

  protected url = '';

  public retrieveAll(): Observable<List<Type>> {
    return this.http.get<List<Type>>(this.url);
  }

  public retrieveOne(id: number): Observable<Type> {
    console.log(this.url + `/${id}`);
    return this.http.get<Type>(this.url + `/${id}`, {
      params: new HttpParams().set('id', id.toString(10))
    });
  }

  public createOne(item: Type): Observable<Type> {
    return this.http.post<Type>(this.url, item);
  }

  public updateOne(id: number, item: Type): Observable<Type> {
    return this.http.put<Type>(this.url + `/${id}`, item, {
      params: new HttpParams().set('id', id.toString(10))
    });
  }

  public deleteOne(id: number): Observable<any> {
    return this.http.delete(this.url + `/${id}`, {
      params: new HttpParams().set('id', id.toString(10))
    });
  }
}
