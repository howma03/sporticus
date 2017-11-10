import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginOverlayComponent} from "./login/login-overlay/login-overlay.component";
import {LandingComponent} from "./landing/landing/landing.component";

const appRoutes: Routes = [
  {
    path: 'landing',
    component: LandingComponent
  },
  {
    path: 'login',
    component: LoginOverlayComponent
  },
  {path: '', redirectTo: '/main/home', pathMatch: 'full'},
  {path: '**', redirectTo: '/main/home'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true} // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
