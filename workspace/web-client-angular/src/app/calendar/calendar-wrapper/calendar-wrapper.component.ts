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
  calendarOptions: Options;
  @ViewChild(CalendarComponent) ucCalendar: CalendarComponent;

  constructor() {}

  ngOnInit() {
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
          start: '2017-11-07T13:00',
          end: '2017-11-07T13:59'
        },
        {
          title: 'Squash',
          start: '2017-11-23T15:00',
          end: '2017-11-23T15:59'
        },
        {
          title: 'Badminton',
          start: '2017-11-23T16:00',
          end: '2017-11-23T16:59'
        }
      ]
    };

    // let el = {
    //   title: 'New event'
    //   start: '2017-10-07',
    //   end: '2017-10-10',
    //   ...
    // }
    // this.ucCalendar.fullCalendar('renderEvent', el);
    // this.ucCalendar.fullCalendar('rerenderEvents');

  }
}
