import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {AuthService} from "./auth.service";
import {AuthGuard} from "./auth.guard";
import {EboxModule} from "@ux-aspects/ux-aspects";
import {MatButtonModule, MatFormFieldModule, MatInputModule, MatSnackBarModule} from "@angular/material";
import {ResetPasswordComponent} from './reset-password/reset-password.component';
import {NewPasswordComponent} from './new-password/new-password.component';
import {FormsModule} from "@angular/forms";
import {ResetPasswordService} from "./reset-password.service";

@NgModule({
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSnackBarModule,
    EboxModule,
    FormsModule
  ],
  exports: [
    LoginComponent, ResetPasswordComponent
  ],
  declarations: [LoginComponent, ResetPasswordComponent, NewPasswordComponent],
  providers: [AuthService, AuthGuard, ResetPasswordService]
})

export class LoginModule {
}
