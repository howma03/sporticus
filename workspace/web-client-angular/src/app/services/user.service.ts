import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/user';

@Injectable()
export class UserService {

  REMOTE_URL = "https://jsonplaceholder.typicode.com";

  constructor(private http: HttpClient) { }

  getItem(id): Observable<User> {
    return this.http.get<User>(this.REMOTE_URL + "/users/" + id);
  }
}
