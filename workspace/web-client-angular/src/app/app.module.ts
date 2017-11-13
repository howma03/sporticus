import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginModule} from "./login/login.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from "./app-routing.module";
import {MainModule} from "./main/main.module";
import {LandingModule} from "./landing/landing.module";
import {RegistrationModule} from "./registration/registration.module";
import {AuthInterceptor} from "./login/auth.interceptor";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    RegistrationModule,
    HttpClientModule,
    LoginModule,
    MainModule,
    AppRoutingModule,
    LandingModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
