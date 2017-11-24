import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {EboxModule} from "@ux-aspects/ux-aspects";
import {MatButtonModule, MatFormFieldModule, MatInputModule, MatSnackBarModule} from "@angular/material";
import {ResetPasswordComponent} from './reset-password/reset-password.component';
import {NewPasswordComponent} from './new-password/new-password.component';
import {FormsModule} from "@angular/forms";
import {ResetPasswordService} from "./reset-password.service";
import {AuthModule} from "../auth/auth.module";

@NgModule({
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSnackBarModule,
    EboxModule,
    FormsModule,
    AuthModule
  ],
  exports: [
    LoginComponent, ResetPasswordComponent
  ],
  declarations: [LoginComponent, ResetPasswordComponent, NewPasswordComponent],
  providers: [ResetPasswordService]
})

export class LoginModule {
}
