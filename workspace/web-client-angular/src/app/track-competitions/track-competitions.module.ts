import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrackCompetitionsComponent } from './track-competitions/track-competitions.component';
import {RestService} from './track-competitions/rest.service';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    TrackCompetitionsComponent
  ],
  declarations: [TrackCompetitionsComponent],
  providers: [
    RestService,
  ]
})
export class TrackCompetitionsModule { }
