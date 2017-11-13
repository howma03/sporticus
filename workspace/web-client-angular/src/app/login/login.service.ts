import {Injectable} from '@angular/core';
import {AuthService} from "./auth.service";

import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {of} from "rxjs/observable/of";

@Injectable()
export class LoginService {

  constructor(private authService: AuthService, private http: HttpClient) {
  }

  public loginUrl = "";

  login(userName: string, password: string): Observable<boolean> {
    let token = "TOKEN";
    this.authService.startSession(userName, token);

    return of(true)
    /*
    return this.http.post<AuthResponse>(this.loginUrl, {
      userName,
      password
    }).map((response: AuthResponse) => {
      let token = response.token;

      if (token) {
        this.authService.startSession(userName, token);
        return true;
      }
      return false;
    });
   */
  }

  logout() {
    //Should we end the session on the server?
    //this.http.delete(this.loginUrl);
    this.authService.endSession();
  }


}


interface AuthResponse {
  token: string;
}
