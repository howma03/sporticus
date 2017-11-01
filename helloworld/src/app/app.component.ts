import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Sporticus';
  sessionId = false;

  doLogin(userName, password) {
    this.sessionId = true;
  }


  doLogout() {
    this.sessionId = false;
  }

}
