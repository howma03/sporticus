import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Router} from "@angular/router";
import 'rxjs/add/operator/do';
import {Observable} from "rxjs/Observable";
import {AuthService} from "./auth.service";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router, private authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.startsWith('/papi/')) {
      //Ignore requests to public api
      return next.handle(req);
    }

    //TODO look at rejecting/holding requests that require auth if the user isn't logged in

    return next.handle(req.clone({
      setHeaders: {
        Authorization: this.authService.getAuthToken()
      }
    })).do(event => {
    }, err => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
          this.router.navigate(['/login'])
        }
      }
    });
  }
}
