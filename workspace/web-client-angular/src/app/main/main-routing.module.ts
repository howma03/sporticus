import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {HomeComponent} from "../home/home/home.component";
import {ManageSportsComponent} from "../manage-sports/manage-sports/manage-sports.component";
import {ManageTeamsComponent} from "../manage-teams/manage-teams/manage-teams.component";
import {ManageCompetitionsComponent} from "../manage-competitions/manage-competitions/manage-competitions.component";
import {TrackCompetitionsComponent} from "../track-competitions/track-competitions/track-competitions.component";
import {MainComponent} from "./main/main.component";
import {AuthGuard} from "../login/auth.guard";

const mainRoutes: Routes = [
  {
    path: 'main',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'manage-sports', component: ManageSportsComponent},
      {path: 'manage-teams', component: ManageTeamsComponent},
      {path: 'manage-competitions', component: ManageCompetitionsComponent},
      {path: 'track-competitions', component: TrackCompetitionsComponent},
      {path: '', redirectTo: '/main/home', pathMatch: 'full'},
      {path: '**', redirectTo: '/main/home', pathMatch: 'full'}
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      mainRoutes,
      {enableTracing: true} // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})
export class MainRoutingModule {
}
