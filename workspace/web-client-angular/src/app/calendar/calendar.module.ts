import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CalendarWrapperComponent} from './calendar-wrapper/calendar-wrapper.component';
import {FullCalendarModule} from "ng-fullcalendar";
import {CalenderEventListComponent} from './calender-event-list/calender-event-list.component';

@NgModule({
  imports: [
    CommonModule,
    FullCalendarModule
  ],
  declarations: [CalendarWrapperComponent, CalenderEventListComponent],
  exports: [CalendarWrapperComponent]
})
export class CalendarModule { }
