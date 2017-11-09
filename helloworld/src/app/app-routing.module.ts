import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "./login/auth.guard";
import {LoginOverlayComponent} from "./login/login-overlay/login-overlay.component";
import {HomeComponent} from "./home/home/home.component";
import {ManageSportsComponent} from "./manage-sports/manage-sports/manage-sports.component";
import {ManageTeamsComponent} from "./manage-teams/manage-teams/manage-teams.component";
import {ManageCompetitionsComponent} from "./manage-competitions/manage-competitions/manage-competitions.component";
import {TrackCompetitionsComponent} from "./track-competitions/track-competitions/track-competitions.component";

const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginOverlayComponent,
    outlet: 'overlay'
  },
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'manage-sports', component: ManageSportsComponent, canActivate: [AuthGuard]},
  {path: 'manage-teams', component: ManageTeamsComponent, canActivate: [AuthGuard]},
  {path: 'manage-competitions', component: ManageCompetitionsComponent, canActivate: [AuthGuard]},
  {path: 'track-competitions', component: TrackCompetitionsComponent, canActivate: [AuthGuard]},
  {path: '**', component: HomeComponent, canActivate: [AuthGuard]}
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
