import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatGridListModule, MatTabsModule} from '@angular/material';
import {ManageSportsModule} from './manage-sports/manage-sports.module';
import {ManageTeamsModule} from './manage-teams/manage-teams.module';
import {TrackCompetitionsModule} from './track-competitions/track-competitions.module';
import {ManageCompetitionsModule} from './manage-competitions/manage-competitions.module';
import {HomeModule} from './home/home.module';
import {ColorServiceModule, DashboardModule, PageHeaderModule, SparkModule} from '@ux-aspects/ux-aspects';
import {HttpClientModule} from '@angular/common/http';

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
    SparkModule,
    TrackCompetitionsModule,
    MatTabsModule,
    MatGridListModule,
    PageHeaderModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
