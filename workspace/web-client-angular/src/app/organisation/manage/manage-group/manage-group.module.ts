import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageGroupMainComponent} from './manage-group-main/manage-group-main.component';
import {ManageGroupMembersComponent} from './manage-group-members/manage-group-members.component';
import {ManageGroupDetailsComponent} from './manage-group-details/manage-group-details.component';
import {ManageGroupEventsComponent} from './manage-group-events/manage-group-events.component';
import {MatButtonModule, MatDatepickerModule, MatInputModule, MatSelectModule, MatTabsModule} from '@angular/material';
import {ServicesModule} from '../../../services/services.module';
import {CreateGroupEventComponent} from './create-group-event/create-group-event.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {EventsModule} from '../../../events/events.module';
import {EventAttendanceManagementComponent} from '../../../events/event-attendance-management/event-attendance-management.component';

import {AddGroupMemberDialogComponent} from './add-group-member-dialog/add-group-member-dialog.component';
import {AddGroupMemberComponent} from './add-group-member/add-group-member.component';
import {CreateGroupComponent} from './create-group/create-group.component';
import {CreateGroupDialogComponent} from './create-group-dialog/create-group-dialog.component';
import {EventAttendanceDialogComponent} from "../../../events/event-attendance-dialog/event-attendance-dialog.component";
import {CreateGroupScheduleComponent} from './create-group-schedule/create-group-schedule.component';
import {WizardModule} from "@ux-aspects/ux-aspects";

@NgModule({
  entryComponents: [
    EventAttendanceDialogComponent,
    EventAttendanceManagementComponent,
    AddGroupMemberDialogComponent,
    CreateGroupDialogComponent,
    CreateGroupScheduleComponent
  ],
  imports: [
    CommonModule,
    EventsModule,
    MatTabsModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    FormsModule,
    ServicesModule,
    MatDatepickerModule,
    WizardModule
  ],
  declarations: [
    ManageGroupMainComponent,
    ManageGroupMembersComponent,
    ManageGroupDetailsComponent,
    ManageGroupEventsComponent,
    CreateGroupEventComponent,
    AddGroupMemberDialogComponent,
    AddGroupMemberComponent,
    CreateGroupComponent,
    CreateGroupDialogComponent,
    CreateGroupScheduleComponent
  ],
  exports: [
    ManageGroupMainComponent
  ]
})
export class ManageGroupModule {
}
