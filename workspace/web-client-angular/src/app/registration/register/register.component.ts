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

  doRegister(userName: string, name: string, email: string, password: string) {
    let testUser: RegisterUser = {
      userName,
      name,
      email,
      password
    };

    this.registrationService.createOne(testUser)
      .subscribe(success => {
        if (success) {
          alert("User " + testUser.name +  " successfully created.")
          this.router.navigate(['/landing/login']);
        }
      }, err => {
        this.errorHandlingService.handleError(err);

        debugger;
        //this.loading = false;
        //this.tryAgain = true;
      });;
  }
}
