import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/catch";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {User} from "../services/users.service";

@Injectable()
export class AuthService {
  constructor(private http: HttpClient) {
  }

  loggedIn: boolean = null;
  currentUser: User = null;

  private loginUrl = "/api/user/profile";

  getCurrentUser(): User {
    return this.currentUser;
  }

  isLoggedIn(): Observable<boolean> {
    if (this.loggedIn == null) {
      return this.check().catch(err => {
        //Assume the worst on an error?
        return Observable.of(false);
      })
    } else {
      return Observable.of(this.loggedIn);
    }
  }

  login(email: string, password: string): Observable<boolean> {
    // let tempToken = "Token";
    // this.authService.startSession(userName, tempToken);
    // return Observable.of(true);
    const token = `${email}:${password}`;

    let headers = new HttpHeaders()
      .set('Authorization', "Basic " + btoa(token));
    return this.doRequest(headers);

  }

  check(): Observable<boolean> {
    return this.doRequest();
  }

  private doRequest(headers: HttpHeaders = new HttpHeaders()): Observable<boolean> {
    return this.http.get<User>(this.loginUrl, {
      headers,
    }).map(user => {
      this.loggedIn = true;
      this.currentUser = user;
      return true;
    }).catch(err => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401 || err.status == 403) {
          this.loggedIn = false;
          this.currentUser = null;
          return Observable.of(false);
        }
      }
      return Observable.throw(err);
    });
  }

  private logoutUrl = '/logout';

  logout() {
    if (this.loggedIn === false) {
      return Observable.of(true);
    }
    this.http.post(this.logoutUrl, '', {
      responseType: "text"
    }).subscribe(() => {
      this.loggedIn = false;
      this.currentUser = null;
    })
  }
}

