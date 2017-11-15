import { Component, OnInit } from '@angular/core';
import {RegisterUser, RegistrationService} from "../../services/registration.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(private registrationService: RegistrationService) { }

  ngOnInit() {
  }

  doRegister(userName: string, email: string, password: string) {
    let testUser: RegisterUser = {
      userName,
      email,
      password
    };

    this.registrationService.createOne(testUser).subscribe();
  }
}
