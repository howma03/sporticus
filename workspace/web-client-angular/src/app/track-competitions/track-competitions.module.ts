import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TrackCompetitionsComponent} from './track-competitions/track-competitions.component';
import {ModelModule} from '../model/model.module';
import {ServicesModule} from "../services/services.module";

@NgModule({
  imports: [
    ServicesModule,
    CommonModule,
    ModelModule
  ],
  exports: [
    TrackCompetitionsComponent
  ],
  declarations: [TrackCompetitionsComponent]
})
export class TrackCompetitionsModule { }
