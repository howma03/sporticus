import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginModule} from "./login/login.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from "./app-routing.module";
import {RegistrationModule} from "./registration/registration.module";
import {AuthInterceptor} from "./auth/auth.interceptor";
import {NgxCarouselModule} from "ngx-carousel";
import 'hammerjs';
import {LadderModule} from "./ladder/ladder.module";
import {OrganisationModule} from "./organisation/organisation.module";
import {NotificationModule} from "./notification/notification.module";
import {FooterComponent} from "./footer/footer.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AuthModule} from "./auth/auth.module";
import {MessageService} from 'primeng/components/common/messageservice';
import {GrowlModule} from 'primeng/primeng';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    NgxCarouselModule,
    HttpClientModule,
    AuthModule.forRoot(),
    RegistrationModule,
    LoginModule,
    LadderModule,
    OrganisationModule,
    NotificationModule,
    // Keep routing module last!
    AppRoutingModule,
    GrowlModule
  ],
  providers: [MessageService,
    {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  }],
  bootstrap: [AppComponent],
  exports: [GrowlModule]
})
export class AppModule { }
