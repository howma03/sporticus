import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "./list";

@Injectable()
export class GroupMemberService {
  constructor(private http: HttpClient) {
  }

  baseUrl = '/api/admin/group';

  public retrieveAll(groupId): Observable<List<Member>> {
    const url = this.baseUrl + `/${groupId}` + '/member';
    return this.http.get<List<Member>>(url);
  }
}

export interface Member {
  id: number;
  name?: string;
}
