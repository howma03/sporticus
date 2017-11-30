import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "./list";

@Injectable()
export class GroupEventService {
  constructor(private http: HttpClient) {
  }

  baseUrl = '/api/admin/group';

  public retrieveAll(groupId): Observable<List<Event>> {
    const url = this.baseUrl + `/${groupId}` + '/event';
    return this.http.get<List<Event>>(url);
  }
}

export interface Event {
  id: number;
  name?: string;
}
