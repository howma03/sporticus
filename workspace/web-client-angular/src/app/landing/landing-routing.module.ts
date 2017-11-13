import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LandingComponent} from "./landing/landing.component";
import {AboutComponent} from "./about/about.component";
import {LegalComponent} from "./legal/legal.component";
import {ContactComponent} from "./contact/contact.component";
import {ClubsComponent} from "./clubs/clubs.component";
import {LoginOverlayComponent} from "../login/login-overlay/login-overlay.component";
import {LegalDisclosurePolicyComponent} from "./legal-disclosure-policy/legal-disclosure-policy.component";
import {HelpComponent} from "./help/help.component";
import {RegisterComponent} from "../registration/register/register.component";

const landingRoutes: Routes = [
  {
    path: 'landing',
    component: LandingComponent,
    children: [
      {path: 'login', component: LoginOverlayComponent},
      {path: 'register', component: RegisterComponent},
      {path: 'about', component: AboutComponent},
      {path: 'help', component: HelpComponent},
      {path: 'help/**', component: HelpComponent},
      {path: 'clubs', component: ClubsComponent},
      {path: 'legal', component: LegalComponent},
      {path: 'legal/disclosure_policy', component: LegalDisclosurePolicyComponent},
      {path: 'contact', component: ContactComponent},
      {path: '', component: AboutComponent},
      {path: '**', redirectTo: '/landing/about'}
    ]
  }
];

@NgModule({
  imports: [

    RouterModule.forChild(
      landingRoutes
    )
  ],
  exports: [
    RouterModule
  ]
})
export class LandingRoutingModule {
}
