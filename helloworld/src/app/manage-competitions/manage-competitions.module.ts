import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ManageCompetitionsComponent} from './manage-competitions/manage-competitions.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    ManageCompetitionsComponent
  ],
  declarations: [ManageCompetitionsComponent]
})
export class ManageCompetitionsModule { }
