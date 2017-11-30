import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventAttendanceManagementComponent } from './event-attendance-management/event-attendance-management.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [EventAttendanceManagementComponent],
  exports: [
    EventAttendanceManagementComponent
  ]
})
export class EventsModule { }
