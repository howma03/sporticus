import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageGroupMainComponent} from './manage-group-main/manage-group-main.component';
import {ManageGroupMembersComponent} from './manage-group-members/manage-group-members.component';
import {ManageGroupDetailsComponent} from './manage-group-details/manage-group-details.component';
import {ManageGroupEventsComponent} from './manage-group-events/manage-group-events.component';
import {MatSelectModule, MatTabsModule} from '@angular/material';
import {ServicesModule} from '../../../services/services.module';
import { CreateGroupEventComponent } from './create-group-event/create-group-event.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    MatTabsModule,
    MatSelectModule,
    FormsModule,
    ServicesModule
  ],
  declarations: [ManageGroupMainComponent, ManageGroupMembersComponent, ManageGroupDetailsComponent, ManageGroupEventsComponent, CreateGroupEventComponent],
  exports: [
    ManageGroupMainComponent
  ]
})
export class ManageGroupModule {
}
