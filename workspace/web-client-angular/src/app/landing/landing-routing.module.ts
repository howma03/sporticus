import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LandingComponent} from "./landing/landing.component";
import {AboutComponent} from "./about/about.component";
import {LegalComponent} from "./legal/legal.component";
import {ContactComponent} from "./contact/contact.component";
import {ClubsComponent} from "./clubs/clubs.component";
import {LoginComponent} from "../login/login/login.component";
import {LegalDisclosurePolicyComponent} from "./legal-disclosure-policy/legal-disclosure-policy.component";
import {HelpComponent} from "./help/help.component";
import {RegisterComponent} from "../registration/register/register.component";
import {InitialComponent} from "./initial/initial.component";
import {ResetPasswordComponent} from "../login/reset-password/reset-password.component";
import {PricesComponent} from "./prices/prices.component";
import {DevelopmentTeamComponent} from "./development-team/development-team.component";
import {Initial2Component} from "./initial-2/initial-2.component";
import {PricesDisplayComponent} from "./prices-display/prices-display.component";

const landingRoutes: Routes = [
  {
    path: '',
    component: LandingComponent,
    children: [
      {path: '', component: Initial2Component},
      {path: 'login', component: LoginComponent},
      {path: 'resetpassword/:email', component: ResetPasswordComponent},
      {path: 'register', component: RegisterComponent},
      {path: 'about', component: AboutComponent},
      {path: 'help', component: HelpComponent},
      {path: 'help/**', component: HelpComponent},
      {path: 'clubs', component: ClubsComponent},
      {path: 'prices', component: PricesDisplayComponent},
      {path: 'development-team', component: DevelopmentTeamComponent},
      {path: 'legal', component: LegalComponent},
      {path: 'legal/disclosure_policy', component: LegalDisclosurePolicyComponent},
      {path: 'contact', component: ContactComponent},
      {path: '**', redirectTo: 'about'}
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
