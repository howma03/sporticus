import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventAttendanceManagementComponent } from './event-attendance-management/event-attendance-management.component';
import {CheckboxModule, SharedModule} from "primeng/primeng";
import {DataTableModule} from "primeng/components/datatable/datatable";
import { EventAttendanceDialogComponent } from './event-attendance-dialog/event-attendance-dialog.component';
import { EditAttendanceDialogComponent } from './edit-attendance-dialog/edit-attendance-dialog.component';
import { EditAttendanceComponent } from './edit-attendance/edit-attendance.component';

@NgModule({
  entryComponents: [
    EditAttendanceDialogComponent
  ],
  imports: [
    CommonModule,
    CheckboxModule,
    DataTableModule,
    SharedModule
  ],
  declarations: [EventAttendanceManagementComponent, EventAttendanceDialogComponent, EditAttendanceDialogComponent, EditAttendanceComponent],
  exports: [
    EventAttendanceDialogComponent,
    EventAttendanceManagementComponent
  ]
})
export class EventsModule { }
