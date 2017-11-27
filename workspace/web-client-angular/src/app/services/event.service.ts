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
  id: number;
  created?: Date;
  createdString?: string;
  dateTime?: Date;
  dateTimeString?: string;
  dateTimeEnd?: Date;
  dateTimeEndString?: string;
  name: string;
  type: string;
  description?: string;
  status?: string;
}
