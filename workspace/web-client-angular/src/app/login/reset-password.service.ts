import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ResetPasswordService {

  constructor(private http: HttpClient) {

  }

  requestReset(email: String): Observable<boolean> {
    return Observable.of(true);
  }

  newPassword(email: String, newPass: String, token: String): Observable<boolean> {
    return Observable.of(true);
  }

}
