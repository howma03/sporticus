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

    this.registrationService.createOne(testUser)
      .subscribe(success => {
        if (success) {
          alert("User " + testUser.firstName + " " + testUser.surname +  " successfully created.")
          this.router.navigate(['/landing/login']);
        }
      }, err => {
        if(err instanceof HttpErrorResponse) {
          if(err.status === 400) {
            alert("A user is already registered with the email=" + testUser.firstName + " " + testUser.surname)
          }
        }

        this.errorHandlingService.handleError(err);

        //this.loading = false;
      });;
  }
}
