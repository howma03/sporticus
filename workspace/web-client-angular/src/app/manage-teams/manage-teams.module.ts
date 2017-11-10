import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageTeamsComponent } from './manage-teams/manage-teams.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    ManageTeamsComponent
  ],
  declarations: [ManageTeamsComponent]
})
export class ManageTeamsModule { }
