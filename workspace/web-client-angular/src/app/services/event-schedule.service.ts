import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class EventScheduleService {

  private baseUrl = '/api/admin/event';

  constructor(private http: HttpClient) {
  }

  public createOne(groupId, item: Schedule): Observable<Schedule> {
    const url = this.baseUrl + `/${groupId}` + '/schedule';
    return this.http.post<Schedule>(url, item);
  }

}

export interface Schedule {
  groupId: number;
  dateFrom: Date;
  dateTo: Date;
  eventName: string;
  event?: Event[];
}
