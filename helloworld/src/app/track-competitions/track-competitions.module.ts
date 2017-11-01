import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrackCompetitionsComponent } from './track-competitions/track-competitions.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    TrackCompetitionsComponent
  ],
  declarations: [TrackCompetitionsComponent]
})
export class TrackCompetitionsModule { }
