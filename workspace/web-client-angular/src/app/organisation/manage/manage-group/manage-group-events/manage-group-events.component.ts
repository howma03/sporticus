import {Component, Input, OnInit} from '@angular/core';
import {GroupEventService} from '../../../../services/group-event.service';
import {CreateGroupEventComponent} from "../create-group-event/create-group-event.component";
import {MatDialog} from "@angular/material";
import {EventAttendanceDialogComponent} from "../../../../events/event-attendance-dialog/event-attendance-dialog.component";

@Component({
  selector: 'app-manage-group-events',
  templateUrl: './manage-group-events.component.html',
  styleUrls: ['./manage-group-events.component.css']
})
export class ManageGroupEventsComponent implements OnInit {

  get group() {
    return this._group;
  }

  @Input()
  set group(value) {
    this._group = value;
    if (value) {
      this.getGroupEvents();
    }
  }

  private _group;

  private events = [];

  private eventsCount = -1;

  constructor(
    public groupEventService: GroupEventService,
    private dialog: MatDialog
  ) {
  }

  ngOnInit() {
  }

  getGroupEvents() {
    this.groupEventService.retrieveAll(this._group.id).subscribe(events => {
      this.events = events.data;
      this.eventsCount = this.events.length;
    });
  }

  public openEventModal(itemId): void {
    let item = this.events.find(item => item.id === itemId);

    let dialogRef = this.dialog.open(CreateGroupEventComponent, {
      data: {
        group: this.group
      },
      height: '900px',
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe((updateRequired) => {
      if (updateRequired) {
        this.getGroupEvents();
      }
    });
  }

  public openAttendanceModal(event): void {

    let dialogRef = this.dialog.open(EventAttendanceDialogComponent, {
      data: {
        group: this.group,
        event: event
      },
      height: '900px',
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe((updateRequired) => {
      if (updateRequired) {
        this.getGroupEvents();
      }
    });
  }
}
