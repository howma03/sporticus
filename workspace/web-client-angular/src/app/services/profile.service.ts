import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {User} from "./users.service";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ProfileService {

  constructor(private http: HttpClient) {
  }

  private profileUrl: string = '/api/user/profile';

  saveUser(user: User): Observable<User> {
    return this.http.put<User>(this.profileUrl, user);
  }

}
