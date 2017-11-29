import { Injectable } from '@angular/core';
import {BaseCrudService} from "./base-crud.service";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class OrganisationGroupsService extends BaseCrudService<Groups> {

  constructor(http: HttpClient) {
    super(http);
  }

  //TODO - This shouldn't use base crud as should support changing the organisation id.
  url = '/api/admin/1';

}

export interface Groups {
  id: number;
  name?: string;
}
