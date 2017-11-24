import {ChangeDetectorRef, Component, EventEmitter, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {CalendarComponent} from 'ng-fullcalendar';
import {Options} from 'fullcalendar';
import {Event, EventService} from '../../services/event.service';
import {MatMenuTrigger, MatMenu} from '@angular/material';

const dateObj = new Date();
const yearMonth = dateObj.getUTCFullYear() + '-' + (dateObj.getUTCMonth() + 1);

@Component({
  selector: 'app-calendar-wrapper',
  templateUrl: './calendar-wrapper.component.html',
  styleUrls: ['./calendar-wrapper.component.css']
})
export class CalendarWrapperComponent implements OnInit {

  calendarOptions: Options;

  public events: Event[] = [];

  /**
   * Set to the x position on a dayClick - the menu is then positioned with this value
   * @type {string}
   */
  menuLeft = "";

  /**
   * Set to the x position on a dayClick - the menu is then positioned with this value
   * @type {string}
   */
  menuTop = "";

  @ViewChild(CalendarComponent) ucCalendar: CalendarComponent;

  @ViewChild(MatMenuTrigger) menu: MatMenuTrigger;

  constructor(private eventService : EventService, private ref: ChangeDetectorRef) {}

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
          editable: false, // true will allow drag/drop - we may support at a later time
          eventLimit: false,
          header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listMonth'
          },
          dayClick: (date, event) => this.dayClick(date, event),
          events: calenderEvents
        };
      });
  }

  clickButton(model: any) {
    console.log('clickButton');
    // this.displayEvent = model;
  }

  /**
   * Trigger the menu to open when a day cell is clicked.
   * The menu trigger must be moved into the click location.
   * @param {Date} date
   * @param jsEvent
   */
  dayClick(date: Date, jsEvent) {
    this.menuTop = jsEvent.clientY + 'px';
    this.menuLeft = jsEvent.clientX + 'px';
    this.menu.openMenu();
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
