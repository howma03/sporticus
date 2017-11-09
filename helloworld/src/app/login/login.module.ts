import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginOverlayComponent} from './login-overlay/login-overlay.component';
import {AuthService} from "./auth.service";
import {AuthGuard} from "./auth.guard";

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    LoginOverlayComponent
  ],
  declarations: [LoginOverlayComponent],
  providers: [AuthService, AuthGuard]
})

export class LoginModule {
}
