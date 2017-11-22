import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "../services/list";

@Injectable()
export class NotificationService {

  constructor(private http: HttpClient) {
  }

  url = '/api/notification';

  public createOne(item: Notification): Observable<Notification> {
    return this.http.post<Notification>(this.url, item);
  }

  public retrieveAll(): Observable<List<Notification>> {
    return this.http.get<List<Notification>>(this.url);
  }
}

export interface Notification {
  id: number;
  severity?: string;
  status?: string;
  text?: string;
  title?: string;
  type?: string;

}
