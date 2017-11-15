import { Injectable } from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";

@Injectable()
export class ErrorHandlingService {

  constructor() { }

  public handleError(err) {
    if(err instanceof HttpErrorResponse) {
      alert(err.statusText)
    } else {
      alert("HTTP messaging is broken.")
    }
  }

}
