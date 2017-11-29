import {
  Component, OnInit, QueryList, ViewChild, ViewChildren
} from '@angular/core';
import {CalendarComponent} from 'ng-fullcalendar';
import {Options} from 'fullcalendar';
import {Event, EventService} from '../../services/event.service';
import {MatDialog, MatMenuTrigger} from '@angular/material';
import {NavItem} from '../nav-item';
import {UnavailableDialogComponent} from '../unavailable-dialog/unavailable-dialog.component';
import {ChallengeService, LadderNode} from '../../services/challenge.service';
import {User} from '../../services/users.service';
import {LadderUser} from '../../services/ladder.service';

@Component({
  selector: 'app-calendar-wrapper',
  templateUrl: './calendar-wrapper.component.html',
  styleUrls: ['./calendar-wrapper.component.css']
})
export class CalendarWrapperComponent implements OnInit {

  calendarOptions: Options;

  /**
   * The current selected date - set by dateClicked
   */
  private dateSelected : Date;

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

  @ViewChildren(MatMenuTrigger) menuTriggers: QueryList<MatMenuTrigger>;


  @ViewChild('dayMenuTrigger') dayMenu: MatMenuTrigger;

  @ViewChild('dynamicMenuTrigger') dynamicMenu: MatMenuTrigger;

  constructor(
    private challengeService : ChallengeService,
    private eventService : EventService,
    private dialog: MatDialog
  ) {}

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


    // Get the available challenges in order to generate a challenge menu
    this.challengeService.getAvailableChallenges()
      .subscribe((ladders : LadderNode[]) => {
        let menuPart = ladders.map(ladder => {

          let childrenAbove = ladder.above.map(user => this.userToMenu(user));
          let childrenBelow = ladder.below.map(user => this.userToMenu(user));

          return {
            displayName: ladder.name,
            iconName: 'group',
            children: childrenAbove.concat(childrenBelow)
          };
        });

        this.navItems = [
          {
            displayName: 'Register Unavailable Times',
            iconName: 'group',
            command: () => this.registerUnavailable()
          },
          {
            displayName: 'Schedule Match',
            iconName: 'games',
            children: menuPart
          }
        ];
      });
  }

  /**
   * Create a user selection item from a user
   * @param {User} user
   * @returns {{displayName: string; iconName: string; route: any}}
   */
  private userToMenu(user : LadderUser) {
    {
      let position = (user.position > 0) ? "above" : "below";
      return {
        displayName: `${user.userName} (${Math.abs(user.position)} ${position})`,
        iconName: 'person',
        route: null
      }
    }
  }

  clickButton(model: any) {
    console.log('clickButton');
  }


  registerUnavailable() {
    let dialogRef = this.dialog.open(UnavailableDialogComponent, {
      disableClose: true,
      data: {
        date: this.dateSelected
      }
    });

    dialogRef.afterClosed().subscribe(challenge => {
      //this.reload();
    });
  }

  /**
   * Trigger the menu to open when a day cell is clicked.
   * The menu trigger must be moved into the click location.
   * @param {Date} date
   * @param jsEvent
   */
  dayClick(date: Date, jsEvent) {
    this.dateSelected = date;
    this.menuTop = jsEvent.clientY + 'px';
    this.menuLeft = jsEvent.clientX + 'px';
    let mt = this.menuTriggers;
    mt.first.openMenu();
  }

  unavailableClicked() {
    alert('set unavailable times');
  }

  scheduleMatchClicked() {
    let mt = this.menuTriggers;
    mt.first.openMenu();
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

  navItems: NavItem[] = [
    {
      displayName: 'Register Unavailable Times',
      iconName: 'group',
      command: () => this.registerUnavailable()
    },
    {
      displayName: 'Schedule Match',
      iconName: 'games'
    }
  ];
}
