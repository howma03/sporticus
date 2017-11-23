import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Subscription";
import 'rxjs/add/observable/of';
import {MatDialog} from "@angular/material";
import {Event, EventService} from "../../services/event.service";

@Component({
  selector: 'app-calender-event-list',
  templateUrl: './calender-event-list.component.html',
  styleUrls: ['./calender-event-list.component.css']
})
export class CalenderEventListComponent implements OnInit {

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
    // this.openModal(itemId);
  }

  updateEventDetails() {
    this.eventService.retrieveAll()
      .map(list => list.data)
      .subscribe((events: Event[]) => {
        this.events = events;
      });
  }

  public deleteEvent(itemId) {
    console.info("delete - id=" + itemId);
    this.eventService.deleteOne(itemId).subscribe(() => {
      this.updateEventDetails();
    });
  }
}
