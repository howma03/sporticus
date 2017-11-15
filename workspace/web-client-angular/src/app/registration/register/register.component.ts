import { Component, OnInit } from '@angular/core';
import {RegisterUser, RegistrationService} from "../../services/registration.service";
import {ErrorHandlingService} from "../../services/error-handling.service";
import {Router} from "@angular/router";

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
      name: this.registrationDetails.name,
      email: this.registrationDetails.email,
      password: this.registrationDetails.password
    };

    this.registrationService.createOne(testUser)
      .subscribe(success => {
        if (success) {
          alert("User " + testUser.name +  " successfully created.")
          this.router.navigate(['/landing/login']);
        }
      }, err => {
        this.errorHandlingService.handleError(err);

        //this.loading = false;
      });;
  }
}
