import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TrackCompetitionsComponent} from './track-competitions/track-competitions.component';
import {ModelModule} from '../model/model.module';
import {ServicesModule} from "../services/services.module";
import {LadderModule} from "../ladder/ladder.module";

@NgModule({
  imports: [
    ServicesModule,
    CommonModule,
    ModelModule,
    LadderModule
  ],
  exports: [
    TrackCompetitionsComponent
  ],
  declarations: [TrackCompetitionsComponent]
})
export class TrackCompetitionsModule { }
