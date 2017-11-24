import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "../services/list";
import {PushService} from "../services/push.service";

@Injectable()
export class NotificationService {

  constructor(
    private http: HttpClient
  ) {
  }

  url = '/api/notification';

  public createOne(item: NotificationInterface): Observable<NotificationInterface> {
    return this.http.post<NotificationInterface>(this.url, item);
  }

  public retrieveAll(): Observable<List<NotificationInterface>> {
    return this.http.get<List<NotificationInterface>>(this.url);
  }
}

export interface NotificationInterface {
  id?: number;
  severity?: string;
  status?: string;
  text: string;
  title?: string;
  type?: string;

}
