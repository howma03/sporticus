import {Component, EventEmitter, OnInit, Output} from '@angular/core';


@Component({
  selector: 'app-login-overlay',
  templateUrl: './login-overlay.component.html',
  styleUrls: ['./login-overlay.component.css']
})

export class LoginOverlayComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

  @Output() login: EventEmitter<String> = new EventEmitter();

  doLogin(userName, password) {
    this.login.emit("TOKEN");
  }

}
