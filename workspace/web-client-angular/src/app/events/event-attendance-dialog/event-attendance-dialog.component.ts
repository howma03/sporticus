import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Group} from "../../services/organisation-groups.service";
import {Event} from "../../services/event.service";

@Component({
  selector: 'app-event-attendance-dialog',
  templateUrl: './event-attendance-dialog.component.html',
  styleUrls: ['./event-attendance-dialog.component.css']
})
export class EventAttendanceDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EventAttendanceDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {

    this.group = data.group;
    this.event = data.event;
  }

  public group: Group;
  public event: Event;

  ngOnInit() {
  }

}
