import {Component, OnInit} from '@angular/core';
import {RegisterUser, RegistrationService} from "../../services/registration.service";
import {ErrorHandlingService} from "../../services/error-handling.service";
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {MatSnackBar} from '@angular/material';
import {Message} from 'primeng/primeng';
import {MessageService} from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  loading = false;
  tryAgain = false;
  tryAgainReason: string = '';

  registrationDetails = {
    firstName: '',
    lastName: '',
    userName: '',
    email: '',
    password: '',
    captchaResponse: ''
  };

  msgs: Message[] = [];

  constructor(private registrationService: RegistrationService,
              private errorHandlingService: ErrorHandlingService,
              private router: Router,
              public snackBar: MatSnackBar,
              private messageService: MessageService) {
  }

  ngOnInit() {
  }

  showResponse(event) {
    this.msgs = [];
    this.messageService.add({
      severity: 'info',
      summary: 'Success',
      detail: 'User Responded'
    });
    this.registrationDetails.captchaResponse = event.response;
  }

  showExpire(event) {
    this.msgs = [];
    this.messageService.add({
      severity: 'warn',
      summary: 'Warning',
      detail: 'Capture expired - need to reload'
    });
  }

  doRegister() {
    const newUser: RegisterUser = {
      userName: this.registrationDetails.userName,
      firstName: this.registrationDetails.firstName,
      lastName: this.registrationDetails.lastName,
      email: this.registrationDetails.email,
      password: this.registrationDetails.password,
      captchaResponse: this.registrationDetails.captchaResponse
    };
    this.loading = true;
    this.tryAgain = false;
    this.registrationService.createOne(newUser)
      .subscribe(success => {
        this.loading = false;
        if (success) {
          this.snack('User "' + newUser.firstName + ' ' +
            newUser.lastName + '" (' + newUser.email + ') registered successfully.');
          this.messageService.add({
            severity: 'success',
            summary: 'User registered',
            detail: 'The user has been successfully registered'
          });
          this.router.navigate(['/landing/login']);
        }
      }, err => {
        this.loading = false;
        if(err instanceof HttpErrorResponse) {
          if (err.status === 406) {
            this.tryAgainReason = "All data fields must be provided";
            if (err.message) {
              this.tryAgainReason = err.message;
            }
            this.tryAgain = true;
            this.messageService.add({
              severity: 'warn',
              summary: 'Required details must be provided',
              detail: this.tryAgainReason
            });
            return;
          }
          if (err.status === 302) {
            this.tryAgainReason = 'A user is already registered with the email - ' + newUser.email;
            this.tryAgain = true;
            this.messageService.add({
              severity: 'warn',
              summary: 'Email already registered',
              detail: 'A user with that email has already been registered'
            });
            return;
          }
          if (err.status === 401) {
            this.tryAgainReason = 'ReCAPTCHA failed';
            this.tryAgain = true;
            this.messageService.add({
              severity: 'warn',
              summary: 'reCAPTCHA failed',
              detail: 'reCaptcha was not entered correctly'
            });
            return;
          }
        }

        // We've handled the error so don't need to pass to the error handling service.
        if (this.tryAgain != true) {
          this.errorHandlingService.handleError(err);
        }
      });
  }

  cancelRegister() {
    this.router.navigate(['/landing/login']);
  }

  snack(message) {
    this.snackBar.open('Message archived', '', {
      duration: 3000
    });
  }
}
