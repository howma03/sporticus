import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatGridListModule, MatTabsModule} from '@angular/material';
import {ManageSportsModule} from './manage-sports/manage-sports.module';
import {ManageTeamsModule} from './manage-teams/manage-teams.module';
import {TrackCompetitionsModule} from './track-competitions/track-competitions.module';
import {ManageCompetitionsModule} from './manage-competitions/manage-competitions.module';
import {HomeModule} from './home/home.module';
import {LoginModule} from "./login/login.module";
import {ColorServiceModule, DashboardModule, PageHeaderModule, SparkModule} from '@ux-aspects/ux-aspects';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home/home.component';
import {ManageCompetitionsComponent} from './manage-competitions/manage-competitions/manage-competitions.component';
import {ManageSportsComponent} from './manage-sports/manage-sports/manage-sports.component';
import {ManageTeamsComponent} from './manage-teams/manage-teams/manage-teams.component';
import {TrackCompetitionsComponent} from './track-competitions/track-competitions/track-competitions.component';
import {LoginOverlayComponent} from "./login/login-overlay/login-overlay.component";
import {AuthGuard} from "./login/auth.guard";


const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginOverlayComponent,
    outlet: 'overlay'
  },
  {path: '**', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'manage-sports', component: ManageSportsComponent, canActivate: [AuthGuard]},
  {path: 'manage-teams', component: ManageTeamsComponent, canActivate: [AuthGuard]},
  {path: 'manage-competitions', component: ManageCompetitionsComponent, canActivate: [AuthGuard]},
  {path: 'track-competitions', component: TrackCompetitionsComponent, canActivate: [AuthGuard]}
];


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HomeModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ManageCompetitionsModule,
    ManageSportsModule,
    ManageTeamsModule,
    ReactiveFormsModule,
    DashboardModule,
    ColorServiceModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    SparkModule,
    TrackCompetitionsModule,
    MatTabsModule,
    MatGridListModule,
    PageHeaderModule,
    LoginModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
