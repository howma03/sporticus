import { Component, OnInit } from '@angular/core';
import {RegisterUser, RegistrationService} from "../../services/registration.service";
import {ErrorHandlingService} from "../../services/error-handling.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(
    private registrationService: RegistrationService,
    private errorHandlingService: ErrorHandlingService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  loading: boolean = false;
  tryAgain: boolean = false;
  tryAgainReason: string = true;

  registrationDetails = {
    firstName: '',
    surname: '',
    userName: '',
    email: '',
    password: ''
  };


  doRegister() {
    let testUser: RegisterUser = {
      userName: this.registrationDetails.userName,
      firstName: this.registrationDetails.firstName,
      surname: this.registrationDetails.surname,
      email: this.registrationDetails.email,
      password: this.registrationDetails.password
    };
    this.loading = true;
    this.tryAgain = false;
    this.registrationService.createOne(testUser)
      .subscribe(success => {
        this.loading = false;
        if (success) {
          alert("User " + testUser.firstName + " " + testUser.surname +  " successfully created.")
          this.router.navigate(['/landing/login']);
        }
      }, err => {
        this.loading = false;
        if(err instanceof HttpErrorResponse) {
          if(err.status === 400) {
            this.tryAgainReason = "A user is already registered with the email - " + testUser.email
            this.tryAgain = true;
          }
        }
        //We've handled the error so don't need to pass to the error handling service.
        if(this.tryAgain = true) {
          this.errorHandlingService.handleError(err);
        }

      });;
  }
}
