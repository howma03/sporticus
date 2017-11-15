import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ResetPasswordService} from "../reset-password.service";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
})
export class ResetPasswordComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router,
              private resetService: ResetPasswordService) {
  }

  ngOnInit() {
    this.userDetails.email = this.route.snapshot.paramMap.get('email');
  }

  userDetails = {
    email: ''
  };

  loading: boolean = false;
  sentEmail: boolean = false;
  tryAgain: boolean = false;

  resetPassword() {
    this.resetService.requestReset(this.userDetails.email)
      .subscribe(result => this.sentEmail = result);
  }

  gotoRegister() {
    this.router.navigate(['register']);
  }

  gotoHome() {
    this.router.navigate(['/'])
  }

}
