import {Injectable} from '@angular/core';
import {BaseCrudService} from "./base-crud.service";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class UsersService extends BaseCrudService<User> {

  constructor(http: HttpClient) {
    super(http);
  }

  url = '/api/admin/user';

}

export interface User {
  id?: number;
  name?: string;
  admin?: boolean;
  firstName: string;
  lastName: string;
  userName?: string;
  email: string;
  password?: string;
}
