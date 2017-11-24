import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CalendarWrapperComponent} from './calendar-wrapper/calendar-wrapper.component';
import {FullCalendarModule} from "ng-fullcalendar";
import {CalendarEventListComponent} from './calendar-event-list/calendar-event-list.component';
import {MatIconModule, MatMenu, MatMenuModule, MatTabsModule} from "@angular/material";
import {CalendarMainComponent} from './calendar-main/calendar-main.component';
import {ServicesModule} from "../services/services.module";
import {HoverActionModule} from "@ux-aspects/ux-aspects";
import {CalendarEventComponent} from './calendar-event/calendar-event.component';
import {FormsModule} from "@angular/forms";
import {MatProgressSpinnerModule} from '@angular/material'

@NgModule({
  imports: [
    CommonModule,
    FullCalendarModule,
    MatIconModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    ServicesModule,
    HoverActionModule,
    FormsModule
  ],
  entryComponents: [CalendarEventComponent, MatMenu],
  declarations: [CalendarWrapperComponent, CalendarEventListComponent, CalendarMainComponent, CalendarEventComponent],
  exports: [CalendarWrapperComponent]
})
export class CalendarModule { }
