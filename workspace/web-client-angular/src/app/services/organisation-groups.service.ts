import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {List} from "./list";

@Injectable()
export class OrganisationGroupsService {

  constructor(private http: HttpClient) {
  }

  baseUrl = '/api/admin/organisation';

  public createOne(organisationId, item: Group): Observable<Group> {
    const url = this.baseUrl + `/${organisationId}` + '/group';
    return this.http.post<Group>(url, item);
  }

  public retrieveAll(organisationId): Observable<List<Group>> {
    const url = this.baseUrl + `/${organisationId}` + '/group';
    return this.http.get<List<Group>>(url);
  }

  public retrieveOne(groupId: number, organisationId: number): Observable<Group> {
    const url = this.baseUrl + `/${organisationId}` + '/group' + `/${groupId}`;
    return this.http.get<Group>(url, {
      params: new HttpParams().set('id', groupId.toString(10))
    });
  }

  public updateOne(groupId: number, organisationId: number, group: Group): Observable<Group> {
    const url = this.baseUrl + `/${organisationId}` + '/group' + `/${groupId}`;
    return this.http.put<Group>(url, group, {
      params: new HttpParams().set('id', organisationId.toString(10))
    });
  }

  public deleteOne(groupId: number, organisationId: number): Observable<any> {
    const url = this.baseUrl + `/${organisationId}` + '/group' + `/${groupId}`;
    return this.http.delete(url, {
      params: new HttpParams().set('id', groupId.toString(10))
    });
  }
}

export interface Group {
  id: number;
  name?: string;
}
