import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CalendarWrapperComponent} from './calendar-wrapper/calendar-wrapper.component';
import {FullCalendarModule} from "ng-fullcalendar";
import {CalenderEventListComponent} from './calender-event-list/calender-event-list.component';
import {MatTabsModule} from "@angular/material";
import {CalenderMainComponent} from './calender-main/calender-main.component';
import {ServicesModule} from "../services/services.module";
import {HoverActionModule} from "@ux-aspects/ux-aspects";

@NgModule({
  imports: [
    CommonModule,
    FullCalendarModule,
    MatTabsModule,
    ServicesModule,
    HoverActionModule
  ],
  declarations: [CalendarWrapperComponent, CalenderEventListComponent, CalenderMainComponent, CalenderEventListComponent],
  exports: [CalendarWrapperComponent]
})
export class CalendarModule { }
