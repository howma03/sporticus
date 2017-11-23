import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "./list";

@Injectable()
export class EventService {

  url = '/api/event';

  constructor(private http: HttpClient) {
  }

  public createOne(item: Notification): Observable<Event> {
    return this.http.post<Event>(this.url, item);
  }

  public retrieveAll(): Observable<List<Event>> {
    return this.http.get<List<Event>>(this.url);
  }

  public deleteOne(id): Observable<any> {
    return this.http.delete(this.url + "/" + id, {
      responseType: 'text'
    });
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
