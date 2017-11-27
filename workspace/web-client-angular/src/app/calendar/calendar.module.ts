import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CalendarWrapperComponent} from './calendar-wrapper/calendar-wrapper.component';
import {FullCalendarModule} from "ng-fullcalendar";
import {CalendarEventListComponent} from './calendar-event-list/calendar-event-list.component';
import {
  MatIconModule, MatMenu, MatMenuModule, MatTabsModule, MatProgressSpinnerModule,
  MatFormFieldModule, MatInputModule
} from "@angular/material";
import {CalendarMainComponent} from './calendar-main/calendar-main.component';
import {ServicesModule} from "../services/services.module";
import {HoverActionModule} from "@ux-aspects/ux-aspects";
import {CalendarEventComponent} from './calendar-event/calendar-event.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MenuItemComponent} from './menu-item/menu-item.component';
import { UnavailableDialogComponent } from './unavailable-dialog/unavailable-dialog.component';
import { UnavailableFormComponent } from './unavailable-form/unavailable-form.component'

@NgModule({
  imports: [
    CommonModule,
    FullCalendarModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    ReactiveFormsModule,
    ServicesModule,
    HoverActionModule,
    FormsModule,


  ],
  entryComponents: [
    CalendarEventComponent,
    MatMenu,
    UnavailableDialogComponent
  ],
  declarations: [
    CalendarWrapperComponent,
    CalendarEventListComponent,
    CalendarMainComponent,
    CalendarEventComponent,
    MenuItemComponent,
    UnavailableDialogComponent,
    UnavailableFormComponent],
  exports: [CalendarWrapperComponent]
})
export class CalendarModule { }
