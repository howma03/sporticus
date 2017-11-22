import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarWrapperComponent } from './calendar-wrapper/calendar-wrapper.component';
import {FullCalendarModule} from "ng-fullcalendar";

@NgModule({
  imports: [
    CommonModule,
    FullCalendarModule
  ],
  declarations: [CalendarWrapperComponent],
  exports: [CalendarWrapperComponent]
})
export class CalendarModule { }
