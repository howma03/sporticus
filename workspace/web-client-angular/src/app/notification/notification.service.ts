import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseCrudService} from "../services/base-crud.service";

@Injectable()
export class NotificationService extends BaseCrudService<NotificationInterface> {

  constructor(http: HttpClient) {
    super(http);
  }

  url = '/api/notification';
  }

  export interface NotificationInterface {
    id?: number;
    severity?: string;
    status?: string;
    text: string;
    title: string;
    type?: string;
  }


