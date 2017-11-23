import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "./list";

@Injectable()
export class EventService {

  url = '/api/event';

  constructor(private http: HttpClient) {
  }

  public createOne(item: Notification): Observable<Notification> {
    return this.http.post<Notification>(this.url, item);
  }

  public retrieveAll(): Observable<List<Event>> {
    return this.http.get<List<Event>>(this.url);
  }
}

export interface Event {
  id: number;
  created: string;
  createdString: string;
  dateTime: string;
  dateTimeString: string;
  name?: string;
  type?: string;
  description?: string;
  status?: string;
}
