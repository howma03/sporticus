import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MainComponent} from './main/main.component';
import {HomeModule} from "../home/home.module";
import {ManageSportsModule} from "../manage-sports/manage-sports.module";
import {ManageTeamsModule} from "../manage-teams/manage-teams.module";
import {TrackCompetitionsModule} from "../track-competitions/track-competitions.module";
import {ManageCompetitionsModule} from "../manage-competitions/manage-competitions.module";
import {MainRoutingModule} from "./main-routing.module";
import {ColorServiceModule, DashboardModule, PageHeaderModule, SparkModule} from "@ux-aspects/ux-aspects";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatGridListModule, MatTabsModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    DashboardModule,
    ColorServiceModule,
    SparkModule,
    MatTabsModule,
    MatGridListModule,
    PageHeaderModule,
    HomeModule,
    ManageSportsModule,
    ManageTeamsModule,
    TrackCompetitionsModule,
    ManageCompetitionsModule,
    MainRoutingModule
  ],
  declarations: [MainComponent]
})
export class MainModule {
}
