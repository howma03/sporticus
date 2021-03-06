import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../auth/auth.service';

@Component({
  selector: 'app-login-overlay',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  loading = false;
  tryAgain = false;

  userDetails = {
    email: '',
    password: ''
  };

  constructor(private router: Router,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.logout();
  }

  doLogin() {
    this.loading = true;
    this.authService.login(this.userDetails.email, this.userDetails.password)
      .subscribe(success => {
        this.loading = false;
        this.tryAgain = !success;
        if (success) {
          this.router.navigate(['main']);
        }
      }, err => {
        this.loading = false;
        this.tryAgain = true;
      });

  }

  gotoRegister() {
    this.router.navigate(['register']);
  }

  gotoResetPassword() {
    this.router.navigate(['resetpassword', this.userDetails.email]);
  }
}
