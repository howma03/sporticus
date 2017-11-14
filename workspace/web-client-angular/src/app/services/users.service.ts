import {Injectable} from '@angular/core';
import {BaseCrudService} from "./base-crud.service";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class UsersService extends BaseCrudService<User> {

  constructor(http: HttpClient) {
    super(http);
  }

  url = '/api/management/user';

}

export interface User {
  id: number;
  name: string;
  userName: string;
  email: string;
}