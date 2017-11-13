import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingComponent } from './landing/landing.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import {ColorServiceModule, DashboardModule, EboxModule, PageHeaderModule, SparkModule} from "@ux-aspects/ux-aspects";
import {LandingRoutingModule} from "./landing-routing.module";
import { AboutComponent } from './about/about.component';
import { LegalComponent } from './legal/legal.component';
import { ContactComponent } from './contact/contact.component';
import { ClubsComponent } from './clubs/clubs.component';
import { HelpComponent } from './help/help.component';
import { OverviewComponent } from './overview/overview.component';
import { LegalDisclosurePolicyComponent } from './legal-disclosure-policy/legal-disclosure-policy.component';

@NgModule({
  imports: [
    CommonModule,
    EboxModule,
    ColorServiceModule,
    DashboardModule,
    PageHeaderModule,
    SparkModule,
    LandingRoutingModule
  ],
  declarations: [LandingComponent, HeaderComponent, FooterComponent, AboutComponent, LegalComponent, ContactComponent, ClubsComponent, HelpComponent, OverviewComponent, LegalDisclosurePolicyComponent ]
})
export class LandingModule { }
