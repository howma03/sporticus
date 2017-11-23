import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class RegistrationService {

  constructor(private http: HttpClient) {
  }

  url = '/papi/registration/user';

  public createOne(item: RegisterUser): Observable<RegisterUser> {
    return this.http.post<RegisterUser>(this.url, item);
  }
}

export interface RegisterUser {
  id?: number;
  firstName?: string;
  lastName?: string;
  userName: string;
  email: string;
  password?: string;
}
