import {Injectable} from '@angular/core';
import {AuthService} from "./auth.service";

import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/catch';

@Injectable()
export class LoginService {

  constructor(private authService: AuthService, private http: HttpClient) {
  }

  public loginUrl = "/papi/user/authenticate";

  login(userName: string, password: string): Observable<boolean> {
    let tempToken = "Token";
    this.authService.startSession(userName, tempToken);
    return Observable.of(true);

    /*

    return this.http.post<AuthResponse>(this.loginUrl + `/${userName}/${password}`, {
      userName,
      password
    }).map((response: AuthResponse) => {
      let token = response.token;

      if (token) {
        this.authService.startSession(userName, token);
        return true;
      }
      return false;
    }).catch(err => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
         return  Observable.of(false);
        }
      }
      return Observable.throw(err);

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
