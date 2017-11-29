import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class GroupEventService {

  baseUrl = '/api/admin/group';

  constructor(private http: HttpClient) {
  }

}
