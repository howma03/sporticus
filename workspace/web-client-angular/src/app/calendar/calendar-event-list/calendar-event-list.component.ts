import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Subscription";
import 'rxjs/add/observable/of';
import {MatDialog} from "@angular/material";
import {Event, EventService} from "../../services/event.service";
import {ConfirmDialogComponent} from "../../shared/confirm-dialog/confirm-dialog.component";
import {CalendarEventComponent} from "../calendar-event/calendar-event.component";

@Component({
  selector: 'app-calendar-event-list',
  templateUrl: './calendar-event-list.component.html',
  styleUrls: ['./calendar-event-list.component.css']
})
export class CalendarEventListComponent implements OnInit {

  public events: Event[] = [];
  private subscription: Subscription;

  constructor(private eventService: EventService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.updateEventDetails();
  }

  public view(itemId) {
    console.info("view - id=" + itemId);
    this.openModal(itemId);
  }

  updateEventDetails() {
    this.eventService.retrieveAll()
      .map(list => list.data)
      .subscribe((events: Event[]) => {
        this.events = events;
      });
  }

  public openModal(itemId): void {
    let item = this.events.find(item => item.id === itemId);

    let dialogRef = this.dialog.open(CalendarEventComponent, {
      data: {
        item: item
      },
      height: '900px',
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe((updateRequired) => {
      if (updateRequired) {
        this.updateEventDetails();
      }
    });
  }

  openDeleteDialog(item) {

    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Confirm delete',
        description: "Are you sure you want to delete the event - " + item.name
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.deleteEvent(item.id);
      }
    });
  }

  public deleteEvent(itemId) {
    this.eventService.deleteOne(itemId).subscribe(() => {
      this.updateEventDetails();
    });
  }
}
