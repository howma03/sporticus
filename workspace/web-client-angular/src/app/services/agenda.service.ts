import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {List} from "./list";

@Injectable()
export class AgendaService {

  constructor(private http: HttpClient) {
  }

  url: string = '/api/event/agenda';

  getAgenda() {
    return this.http.get<List<CalenderEvent>>(this.url);
  }
}

export interface CalenderEvent {
  dateTime: Date,
  name: string,
  description: string
}
