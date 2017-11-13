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
import {NgxCarouselModule} from "ngx-carousel";
import 'hammerjs';
import {ManageModule} from "./manage/manage.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    NgxCarouselModule,
    BrowserModule,
    RegistrationModule,
    HttpClientModule,
    LoginModule,
    MainModule,
    AppRoutingModule,
    LandingModule,
    ManageModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
