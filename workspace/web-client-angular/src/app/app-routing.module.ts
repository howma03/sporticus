import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainModule} from "./main/main.module";
import {LandingModule} from "./landing/landing.module";

const appRoutes: Routes = [
  {
    path: '', redirectTo: 'main',
    pathMatch: 'full'
  },
  {
    path: 'login',
    redirectTo: 'landing/login'
  },
  {
    path: 'register',
    redirectTo: 'landing/register'
  },
  {
    path: 'resetpassword/:email',
    redirectTo: 'landing/resetpassword/:email'
  },
  {
    path: 'landing',
    loadChildren: () => LandingModule
  },
  {
    path: 'main',
    loadChildren: () => MainModule
  },
  {path: '**', redirectTo: 'main'}
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
