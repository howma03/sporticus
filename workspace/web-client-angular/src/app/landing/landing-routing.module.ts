import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LandingComponent} from "./landing/landing.component";
import {AboutComponent} from "./about/about.component";
import {LegalComponent} from "./legal/legal.component";
import {ContactComponent} from "./contact/contact.component";

const landingRoutes: Routes = [
  {
    path: 'landing',
    component: LandingComponent,
    children: [
      {path: 'about', component: AboutComponent},
      {path: 'legal', component: LegalComponent},
      {path: 'contact', component: ContactComponent},
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
