import {Component, Inject, OnInit} from '@angular/core';
import {Event, EventService} from "../../services/event.service";
import {ErrorHandlingService} from "../../services/error-handling.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-calendar-event',
  templateUrl: './calendar-event.component.html',
  styleUrls: ['./calendar-event.component.css']
})
export class CalendarEventComponent implements OnInit {

  editMode: boolean = false;
  submitText: string = "CREATE";
  creationDescription: String = "To create a new event, please enter a few details";
  eventDetails = {
    id: '',
    name: '',
    type: '',
    description: '',
    status: ''
  };

  constructor(private eventService: EventService,
              private errorHandlingService: ErrorHandlingService,
              public dialogRef: MatDialogRef<CalendarEventComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    if (this.data.item !== undefined) {
      this.eventDetails.id = this.data.item.id;
      this.eventDetails.name = this.data.item.name;
      this.eventDetails.description = this.data.item.description;
      this.eventDetails.type = this.data.item.type;
      this.eventDetails.status = this.data.item.status;

      this.editMode = true;
      this.submitText = "SAVE";
      this.creationDescription = "This allows the user to modify the details for the event"
    }
  }

  makeChange() {
    let event: Event = {
      id: this.eventDetails.id,
      name: this.eventDetails.name,
      type: this.eventDetails.type,
      description: this.eventDetails.description,
      status: this.eventDetails.status
    };

    if (this.editMode === true) {
      this.edit(event)
    } else {
      this.add(event)
    }
  }

  add(event) {
    this.eventService.createOne(event)
      .subscribe(success => {
        if (success) {
          alert("The event - " + event.name + " successfully created.");
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  edit(event) {
    this.eventService.updateOne(event.id, event)
      .subscribe(success => {
        if (success) {
          alert("The event - " + event.name + " successfully updated.");
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  cancel() {
    this.closeWindow(false)
  }

  closeWindow(requiresUpdate: boolean) {
    this.dialogRef.close(requiresUpdate);
  }
}
