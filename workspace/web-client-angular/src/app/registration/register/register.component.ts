import {Component, OnInit} from '@angular/core';
import {RegisterUser, RegistrationService} from "../../services/registration.service";
import {ErrorHandlingService} from "../../services/error-handling.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(
    private registrationService: RegistrationService,
    private errorHandlingService: ErrorHandlingService,
    private router: Router,
    public snackBar: MatSnackBar
  ) { }

  ngOnInit() {
  }

  loading: boolean = false;
  tryAgain: boolean = false;
  tryAgainReason: string = "";

  registrationDetails = {
    firstName: '',
    lastName: '',
    userName: '',
    email: '',
    password: ''
  };

  doRegister() {
    let newUser: RegisterUser = {
      userName: this.registrationDetails.userName,
      firstName: this.registrationDetails.firstName,
      lastName: this.registrationDetails.lastName,
      email: this.registrationDetails.email,
      password: this.registrationDetails.password
    };
    this.loading = true;
    this.tryAgain = false;
    this.registrationService.createOne(newUser)
      .subscribe(success => {
        this.loading = false;
        if (success) {
          this.snack("User '" + newUser.firstName + " " + newUser.lastName + "' (" + newUser.email + ") registered successfully.");
          this.router.navigate(['/landing/login']);
        }
      }, err => {
        this.loading = false;
        if(err instanceof HttpErrorResponse) {
          if(err.status === 400) {
            this.tryAgainReason = "A user is already registered with the email - " + newUser.email;
            this.tryAgain = true;
          }
        }
        //We've handled the error so don't need to pass to the error handling service.
        if(this.tryAgain = true) {
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
