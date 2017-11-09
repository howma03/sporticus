import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {of} from "rxjs/observable/of";

@Injectable()
export class AuthService {

  public token: string;

  constructor(private http: HttpClient) {
  }

  private loginUrl = "";

  getSession(userName: string, password: string): Observable<boolean> {
    let token = "TOKEN";
    this.token = token;
    localStorage.setItem('currentUser', JSON.stringify({userName, token}));
    return of(true)
    /*
    return this.http.post<AuthResponse>(this.loginUrl, {
      userName,
      password
    }).map((response: AuthResponse) => {
      let token = response.token;

      if (token) {
        this.token = token;
        localStorage.setItem('currentUser', JSON.stringify({userName, token}));
        return true;
      }
      return false;
    });
   */
  }

  endSession() {
    //Should we end the session on the server?
    //this.http.delete(this.loginUrl);
    this.token = null;
    localStorage.removeItem('currentUser');
  }

}

interface AuthResponse {
  token: string;
}
