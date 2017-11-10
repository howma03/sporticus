import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingComponent } from './landing/landing.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ColorServiceModule, DashboardModule, PageHeaderModule, SparkModule} from "@ux-aspects/ux-aspects";
import {LandingRoutingModule} from "./landing-routing.module";
import { AboutComponent } from './about/about.component';
import { LegalComponent } from './legal/legal.component';
import { ContactComponent } from './contact/contact.component';

@NgModule({
  imports: [
    CommonModule,
    ColorServiceModule,
    DashboardModule,
    PageHeaderModule,
    SparkModule,
    LandingRoutingModule
  ],
  declarations: [LandingComponent, HeaderComponent, FooterComponent, AboutComponent, LegalComponent, ContactComponent ]
})
export class LandingModule { }
