import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventAttendanceManagementComponent } from './event-attendance-management/event-attendance-management.component';
import {SharedModule} from "primeng/primeng";
import {DataTableModule} from "primeng/components/datatable/datatable";
import { EventAttendanceDialogComponent } from './event-attendance-dialog/event-attendance-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    DataTableModule,
    SharedModule
  ],
  declarations: [EventAttendanceManagementComponent, EventAttendanceDialogComponent],
  exports: [
    EventAttendanceDialogComponent,
    EventAttendanceManagementComponent
  ]
})
export class EventsModule { }
