import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { CalendarComponent } from 'ng-fullcalendar';
import { Options } from 'fullcalendar';

@Component({
  selector: 'app-calendar-wrapper',
  templateUrl: './calendar-wrapper.component.html',
  styleUrls: ['./calendar-wrapper.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CalendarWrapperComponent implements OnInit {
  private calendarOptions: Options;

  @ViewChild(CalendarComponent) ucCalendar: CalendarComponent;

  constructor() {}

  ngOnInit() {

    const dateObj = new Date();
    const yearMonth = dateObj.getUTCFullYear() + '-' + (dateObj.getUTCMonth() + 1);

    this.calendarOptions = {
      editable: true,
      eventLimit: false,
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay,listMonth'
      },
      events: [
        {
          title: 'Badminton',
          start: `${yearMonth}-07T13:00`,
          end: `${yearMonth}-07T13:59`
        },
        {
          title: 'Squash',
          start: `${yearMonth}-23T15:00`,
          end: `${yearMonth}-23T15:59`
        },
        {
          title: 'Badminton',
          start: `${yearMonth}-23T16:00`,
          end: `${yearMonth}-23T16:59`
        }
      ]
    };
  }
}
