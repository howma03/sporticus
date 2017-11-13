import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrackCompetitionsComponent } from './track-competitions/track-competitions.component';
import {ModelModule} from '../model/model.module';
import {UserService} from './track-competitions/user.service';

@NgModule({
  imports: [
    CommonModule,
    ModelModule
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
