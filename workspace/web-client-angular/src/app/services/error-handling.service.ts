import {Injectable} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "primeng/components/common/messageservice";

@Injectable()
export class ErrorHandlingService {

  constructor(private messageService: MessageService) {
  }

  public handleError(err) {
    if(err instanceof HttpErrorResponse) {
      this.messageService.add({
        severity: 'warn',
        summary: 'HTTPS Failure',
        detail: err.statusText
      });
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'HTTPS Failure',
        detail: 'HTTP messaging is broken.'
      });
    }
  }

}
