import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseCrudService} from "./base-crud.service";

@Injectable()
export class EventService extends BaseCrudService<Event> {

  url = '/api/event';

  constructor(http: HttpClient) {
    super(http);
  }
}

export interface Event {
  id: string;
  created?: string;
  createdString?: string;
  dateTime?: string;
  dateTimeString?: string;
  name: string;
  type: string;
  description?: string;
  status?: string;
}
