import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "./list";

@Injectable()
export class EventAttendanceService {

  constructor(
    private http: HttpClient
  ) { }

  baseUrl = '/api/admin/event';

  public retrieveAll(eventId, expanded: Boolean): Observable<List<Attendance>> {
    const url = this.baseUrl + `/${eventId}` + '/attendance';

    return this.http.get<List<Attendance>>(url, {
      params: new HttpParams().set('expanded', expanded.toString())
    });
  }

  public createOne(eventId, item: Attendance): Observable<Attendance> {
    const url = this.baseUrl + `/${eventId}` + '/attendance';
    return this.http.post<Attendance>(url, item);
  }

  public updateOne(eventId: number, attendanceId: number, attendance: Attendance): Observable<Attendance> {
    const url = this.baseUrl + `/${eventId}` + '/attendance' + `/${attendanceId}`;
    return this.http.put<Attendance>(url, attendance, {
      params: new HttpParams().set('id', eventId.toString(10))
    });
  }

}

export interface Attendance {
  id?: number;
  name?: string;
}
