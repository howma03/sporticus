import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginOverlayComponent} from "./login/login-overlay/login-overlay.component";
import {LandingComponent} from "./landing/landing/landing.component";
import {RegisterComponent} from "./registration/register/register.component";

const appRoutes: Routes = [
  {
    path: 'login',
    redirectTo: 'landing/login'
  },
  {
    path: 'register',
    redirectTo: 'landing/register'
  },
  {
    path: 'manage',
    redirectTo: 'manage/main'
  },
  {path: '', redirectTo: '/main/home', pathMatch: 'full'},
  //{path: '**', redirectTo: '/main/home'}
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
