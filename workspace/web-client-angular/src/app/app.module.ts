import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginModule} from "./login/login.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from "./app-routing.module";
import {RegistrationModule} from "./registration/registration.module";
import {AuthInterceptor} from "./login/auth.interceptor";
import {NgxCarouselModule} from "ngx-carousel";
import 'hammerjs';
import {LadderModule} from "./ladder/ladder.module";
import {OrganisationModule} from "./organisation/organisation.module";
import {NotificationModule} from "./notification/notification.module";
import {FooterComponent} from "./footer/footer.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    NgxCarouselModule,
    RegistrationModule,
    HttpClientModule,
    LoginModule,
    LadderModule,
    OrganisationModule,
    NotificationModule,
    // Keep routing module last!
    AppRoutingModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
