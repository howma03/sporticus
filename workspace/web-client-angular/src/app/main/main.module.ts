import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MainComponent} from './main/main.component';
import {HomeModule} from "../home/home.module";
import {TrackCompetitionsModule} from "../track-competitions/track-competitions.module";
import {MainRoutingModule} from "./main-routing.module";
import {ColorServiceModule, DashboardModule, PageHeaderModule, SparkModule} from "@ux-aspects/ux-aspects";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatDialogModule, MatGridListModule, MatTabsModule} from "@angular/material";
import {ProfileModule} from "../profile/profile.module";
import {AdminModule} from "../admin/admin.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    DashboardModule,
    ColorServiceModule,
    SparkModule,
    MatDialogModule,
    MatTabsModule,
    MatGridListModule,
    PageHeaderModule,
    HomeModule,
    AdminModule,
    TrackCompetitionsModule,
    ProfileModule,

    //Keep Routing Module at the bottom
    MainRoutingModule
  ],
  declarations: [MainComponent]
})
export class MainModule {
}
