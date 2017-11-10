import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageSportsComponent } from './manage-sports/manage-sports.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    ManageSportsComponent
  ],
  declarations: [ManageSportsComponent]
})
export class ManageSportsModule { }
