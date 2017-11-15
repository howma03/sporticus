import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {AuthService} from "./auth.service";
import {AuthGuard} from "./auth.guard";
import {EboxModule} from "@ux-aspects/ux-aspects";
import {MatFormFieldModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    MatFormFieldModule,
    EboxModule
  ],
  exports: [
    LoginComponent
  ],
  declarations: [LoginComponent],
  providers: [AuthService, AuthGuard]
})

export class LoginModule {
}
