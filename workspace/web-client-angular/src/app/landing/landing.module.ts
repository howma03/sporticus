import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LandingComponent} from './landing/landing.component';
import {HeaderComponent} from './header/header.component';
import {ColorServiceModule, DashboardModule, EboxModule, PageHeaderModule, SparkModule} from "@ux-aspects/ux-aspects";
import {LandingRoutingModule} from "./landing-routing.module";
import {AboutComponent} from './about/about.component';
import {LegalComponent} from './legal/legal.component';
import {ContactComponent} from './contact/contact.component';
import {ClubsComponent} from './clubs/clubs.component';
import {HelpComponent} from './help/help.component';
import {OverviewComponent} from './overview/overview.component';
import {LegalDisclosurePolicyComponent} from './legal-disclosure-policy/legal-disclosure-policy.component';
import {InitialComponent} from './initial/initial.component';
import {NgxCarouselModule} from "ngx-carousel";
import {PricesComponent} from './prices/prices.component';
import {LoginModule} from "../login/login.module";
import {RegistrationModule} from "../registration/registration.module";
import { DevelopmentTeamComponent } from './development-team/development-team.component';
import { Initial2Component } from './initial-2/initial-2.component';
import {AngularFontAwesomeModule} from "angular-font-awesome";
import { PricesDisplayComponent } from './prices-display/prices-display.component';

@NgModule({
  imports: [
    LoginModule,
    RegistrationModule,
    CommonModule,
    NgxCarouselModule,
    EboxModule,
    ColorServiceModule,
    DashboardModule,
    PageHeaderModule,
    SparkModule,
    LandingRoutingModule,
    AngularFontAwesomeModule
  ],
  exports: [],
  declarations: [LandingComponent, HeaderComponent, AboutComponent, LegalComponent, ContactComponent,
    ClubsComponent, HelpComponent, OverviewComponent, LegalDisclosurePolicyComponent, InitialComponent,
    PricesComponent, DevelopmentTeamComponent, Initial2Component, PricesDisplayComponent]
})
export class LandingModule { }
