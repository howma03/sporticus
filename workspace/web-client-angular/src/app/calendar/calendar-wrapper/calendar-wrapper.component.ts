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
          dayClick: (moment) => this.dayClick(moment),
          events: calenderEvents
        };
      });
  }

  clickButton(model: any) {
    console.log('clickButton');
    // this.displayEvent = model;
  }

  dayClick(moment: any) {
    alert('dayClick on ' + moment._d);
    debugger;
    // this.displayEvent = model;
  }

  eventClick(model: any) {
    alert('eventClick');
    // model = {
    //   event: {
    //     id: model.event.id,
    //     start: model.event.start,
    //     end: model.event.end,
    //     title: model.event.title,
    //     allDay: model.event.allDay
    //     // other params
    //   },
    //   duration: {}
    // }
    // this.displayEvent = model;
  }
  updateEvent(model: any) {
    alert('updateEvent');
    // model = {
    //   event: {
    //     id: model.event.id,
    //     start: model.event.start,
    //     end: model.event.end,
    //     title: model.event.title
    //     // other params
    //   },
    //   duration: {
    //     _data: model.duration._data
    //   }
    // }
    // this.displayEvent = model;
  }
}
