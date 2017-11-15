import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginOverlayComponent} from './login-overlay/login-overlay.component';
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
    LoginOverlayComponent
  ],
  declarations: [LoginOverlayComponent],
  providers: [AuthService, AuthGuard]
})

export class LoginModule {
}
