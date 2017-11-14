import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  orientation: string = 'vertical';
  constructor() { }

  ngOnInit() {
  }

  doRegister(userName: string, email: string, password: string) {
    // this.usersService.createOne(user).subscribe(user => alert("User created successfully for " + user.username));
  }
}
