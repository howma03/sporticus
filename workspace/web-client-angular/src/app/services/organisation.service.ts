import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {BaseCrudService} from "./base-crud.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class OrganisationService extends BaseCrudService<Organisation> {

  constructor(http: HttpClient) {
    super(http);
  }

  url = '/api/organisation';

  public getOrganisationByUrlFragment(urlFragment: string): Observable<organisationFromFragment> {
    debugger;
    return this.http.get<organisationFromFragment>(this.url + "/findByUrlFragment", {
      params: new HttpParams().set('urlFragment', urlFragment)
    });
  }
}

export interface organisationFromFragment {
  name: string;
  urlFragment: string;

  domain?: string;
  id?: number;
  created?: Date,
  createdString?: string;
  ownerId?: number;
  isEnabled?: boolean;
  ownerEmail?: string;
  address?: string;

}

export interface Organisation {
  name: string;
  urlFragment: string;

  domain?: string;
  id?: number;
  created?: Date,
  createdString?: string;
  ownerId?: number;
  isEnabled?: boolean;
  ownerEmail?: string;
  address?: string;

}
