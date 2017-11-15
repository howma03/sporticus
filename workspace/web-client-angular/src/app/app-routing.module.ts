import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

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
    path: 'resetpassword/:email',
    redirectTo: 'landing/resetpassword/:email'
  },
  {
    path: 'manage',
    redirectTo: 'main/manage'
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
