import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HttpModule} from '@angular/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatGridListModule, MatTabsModule} from '@angular/material';
import {ManageSportsModule} from './manage-sports/manage-sports.module';
import {ManageTeamsModule} from './manage-teams/manage-teams.module';
import {TrackCompetitionsModule} from './track-competitions/track-competitions.module';
import {ManageCompetitionsModule} from './manage-competitions/manage-competitions.module';
import {HomeModule} from './home/home.module';
import {ColorServiceModule, DashboardModule, SparkModule} from '@ux-aspects/ux-aspects';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HomeModule,
    HttpModule,
    BrowserAnimationsModule,
    ManageCompetitionsModule,
    ManageSportsModule,
    ManageTeamsModule,
    ReactiveFormsModule,
    DashboardModule,
    ColorServiceModule,
    SparkModule,
    TrackCompetitionsModule,
    MatTabsModule,
    MatGridListModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
