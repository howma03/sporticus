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

const landingRoutes: Routes = [
  {
    path: 'landing',
    component: LandingComponent,
    children: [
      {path: 'about', component: AboutComponent},
      {path: 'help', component: HelpComponent},
      {path: 'help/**', component: HelpComponent},
      {path: 'clubs', component: ClubsComponent},
      {path: 'login', component: LoginOverlayComponent},
      {path: 'legal', component: LegalComponent},
      {path: 'legal/disclosure_policy', component: LegalDisclosurePolicyComponent},
      {path: 'contact', component: ContactComponent},
      {path: '', redirectTo: '/landing/about', pathMatch: 'full'},
      {path: '**', redirectTo: '/landing/about'}
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      landingRoutes,
      {enableTracing: true} // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})
export class LandingRoutingModule {
}
