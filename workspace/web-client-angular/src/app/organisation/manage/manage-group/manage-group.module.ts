import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageGroupMainComponent} from './manage-group-main/manage-group-main.component';
import {ManageGroupMembersComponent} from './manage-group-members/manage-group-members.component';
import {ManageGroupDetailsComponent} from './manage-group-details/manage-group-details.component';
import {ManageGroupEventsComponent} from './manage-group-events/manage-group-events.component';
import {MatSelectModule, MatTabsModule} from '@angular/material';
import {ServicesModule} from '../../../services/services.module';

@NgModule({
  imports: [
    CommonModule,
    MatTabsModule,
    MatSelectModule,
    ServicesModule
  ],
  declarations: [ManageGroupMainComponent, ManageGroupMembersComponent, ManageGroupDetailsComponent, ManageGroupEventsComponent],
  exports: [ManageGroupMainComponent]
})
export class ManageGroupModule {
}
