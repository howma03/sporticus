import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageGroupMainComponent} from './manage-group-main/manage-group-main.component';
import {ManageGroupMembersComponent} from './manage-group-members/manage-group-members.component';
import {ManageGroupDetailsComponent} from './manage-group-details/manage-group-details.component';
import {ManageGroupEventsComponent} from './manage-group-events/manage-group-events.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [ManageGroupMainComponent, ManageGroupMembersComponent, ManageGroupDetailsComponent, ManageGroupEventsComponent],
  exports: [ManageGroupMainComponent]
})
export class ManageGroupModule {
}
