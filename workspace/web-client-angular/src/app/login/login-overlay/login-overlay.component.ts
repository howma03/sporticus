import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-overlay',
  templateUrl: './login-overlay.component.html',
  styleUrls: ['./login-overlay.component.css']
})

export class LoginOverlayComponent implements OnInit {

  constructor(private router: Router,
              private loginService: AuthService) {
  }

  ngOnInit() {
    this.loginService.endSession();
  }

  loading: boolean = false;
  tryAgain: boolean = false;

  doLogin(userName: string, password: string) {
    this.loading = true;
    this.loginService.getSession(userName, password)
      .subscribe(success => {
        this.loading = false;
        this.tryAgain = !success;
        if (success) {
          this.router.navigate(['/main']);
        }
      }, fail => {
        this.loading = false;
        this.tryAgain = true;
      });

  }

  gotoRegister() {
    this.router.navigate(['register'])
  }
}
