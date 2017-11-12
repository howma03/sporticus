import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrackCompetitionsComponent } from './track-competitions/track-competitions.component';
import {UserService} from './track-competitions/user.service';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    TrackCompetitionsComponent
  ],
  declarations: [TrackCompetitionsComponent],
  providers: [
    UserService,
  ]
})
export class TrackCompetitionsModule { }
