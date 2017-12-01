import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {List} from './list';
import {Event} from './event.service';

@Injectable()
export class GroupEventService {

  constructor(private http: HttpClient) {
  }

  baseUrl = '/api/admin/group';

  public retrieveAll(groupId): Observable<List<Event>> {
    const url = this.baseUrl + `/${groupId}` + '/event';
    return this.http.get<List<Event>>(url);
  }

  public createOne(groupId, item: Event): Observable<Event> {
    const url = this.baseUrl + `/${groupId}` + '/event';
    return this.http.post<Event>(url, item);
  }

  public updateOne(groupId: number, eventId: number, event: Event): Observable<Event> {
    const url = this.baseUrl + `/${groupId}` + '/event' + `/${eventId}`;
    return this.http.put<Event>(url, event, {
      params: new HttpParams().set('id', eventId.toString(10))
    });
  }
}

export * from './event.service';
