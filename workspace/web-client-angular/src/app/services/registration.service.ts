import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class RegistrationService<RegisterUser> {

  constructor(private http: HttpClient) {
  }

  url = '/papi/registration/user';

  public createOne(item: RegisterUser): Observable<RegisterUser> {
    return this.http.post<RegisterUser>(this.url, item);
  }
}

export interface RegisterUser {
  id?: number;
  name?: string;
  userName: string;
  email: string;
  password?: string;
}
