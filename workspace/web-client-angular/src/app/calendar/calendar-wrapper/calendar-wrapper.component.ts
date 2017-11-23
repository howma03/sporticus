import {AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import { CalendarComponent } from 'ng-fullcalendar';
import { Options } from 'fullcalendar';
import {EventService, Event} from '../../services/event.service';

const dateObj = new Date();
const yearMonth = dateObj.getUTCFullYear() + '-' + (dateObj.getUTCMonth() + 1);

@Component({
  selector: 'app-calendar-wrapper',
  templateUrl: './calendar-wrapper.component.html',
  styleUrls: ['./calendar-wrapper.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CalendarWrapperComponent implements OnInit {
  private calendarOptions: Options;

  public events: Event[] = [];

  @ViewChild(CalendarComponent) ucCalendar: CalendarComponent;

  constructor(private eventService : EventService) {}

  ngOnInit() {
    this.eventService.retrieveAll()
      .map(list=>list.data)
      .subscribe((events: Event[])=>{
        let calenderEvents = events.map(event => {
          return {
            title: event.description,
              start: event.dateTime
          }
        });
        this.calendarOptions = {
          editable: true,
          eventLimit: false,
          header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listMonth'
          },
          events: calenderEvents
        };
      });
  }
}
