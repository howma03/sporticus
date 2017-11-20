import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseCrudService} from "./base-crud.service";

@Injectable()
export class OrganisationService extends BaseCrudService<Organisation> {

  constructor(http: HttpClient) {
    super(http);
  }

  url = '/api/management/organisation';
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
