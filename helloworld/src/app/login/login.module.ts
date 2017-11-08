import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginOverlayComponent} from './login-overlay/login-overlay.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    LoginOverlayComponent
  ],
  declarations: [LoginOverlayComponent]
})
export class LoginModule {
}
