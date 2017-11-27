import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BaseCrudService} from './base-crud.service';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class OrganisationService extends BaseCrudService<Organisation> {

  constructor(http: HttpClient) {
    super(http);
  }

  url = '/api/organisation';

  public getOrganisationByUrlFragment(urlFragment: string): Observable<OrganisationFromFragment> {
    return this.http.get<OrganisationFromFragment>(this.url + '/findByUrlFragment/' + urlFragment, {
    });
  }
}

export interface OrganisationFromFragment {
  name: string;
  urlFragment: string;

  domain?: string;
  id?: number;
  created?: Date;
  ownerId?: number;
  isEnabled?: boolean;
  ownerEmail?: string;
  address?: string;
  postcode?: string;
}

export interface Organisation {
  name: string;
  urlFragment: string;

  domain?: string;
  id?: number;
  created?: Date;
  createdString?: string;
  ownerId?: number;
  isEnabled?: boolean;
  ownerEmail?: string;
  address?: string;
  postcode?: string;

}
