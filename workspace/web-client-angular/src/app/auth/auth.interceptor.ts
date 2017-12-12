import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Router} from "@angular/router";
import 'rxjs/add/operator/do';
import {Observable} from "rxjs/Observable";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.startsWith('/papi/')) {
      //Ignore requests to public api
      return next.handle(req);
    }
    if (req.headers.has('skipAuth')) {
      let newRequest = req.clone({headers: req.headers.delete('skipAuth')});
      return next.handle(newRequest);
    }

    //TODO look at rejecting/holding requests that require auth if the user isn't logged in

    return next.handle(req).do(event => {
    }, err => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401 || err.status === 403) {
          this.router.navigate(['/landing/login'])
        }
      }
    });
  }
}
