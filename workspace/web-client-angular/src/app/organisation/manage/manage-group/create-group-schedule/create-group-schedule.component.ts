import {Component, Inject, OnInit} from '@angular/core';
import {ErrorHandlingService} from "../../../../services/error-handling.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Group} from "../../../../services/organisation-groups.service";
import {EventScheduleService, Schedule} from "../../../../services/event-schedule.service";

@Component({
  selector: 'app-create-group-schedule',
  templateUrl: './create-group-schedule.component.html',
  styleUrls: ['./create-group-schedule.component.css']
})
export class CreateGroupScheduleComponent implements OnInit {

  details: Schedule = <Schedule>{};
  submitText = 'Test';
  editMode = false;
  group: Group;

  constructor(public eventScheduleService: EventScheduleService,
              private errorHandlingService: ErrorHandlingService,
              public dialogRef: MatDialogRef<CreateGroupScheduleComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this._organisationId = data.organisationId;
    this.group = data.group;
  }

  private _organisationId: number;

  get organisationId(): number {
    return this._organisationId;
  }

  set organisationId(value: number) {
    this._organisationId = value;
  }

  ngOnInit() {
  }

  submit() {
    const schedule: Schedule = {
      groupId: this.details.groupId,
      dateFrom: this.details.dateFrom,
      dateTo: this.details.dateTo,
      eventName: this.details.eventName
    };

    if (this.editMode === true) {
      this.performUpdateOperation(schedule);
    } else {
      this.performAddOperation(schedule);
    }
  }

  performAddOperation(entity) {
    this.eventScheduleService.createOne(this.group.id, entity)
      .subscribe(success => {
        if (success) {
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  performUpdateOperation(entity) {

  }

  closeWindow(requiresUpdate: boolean) {
    this.dialogRef.close(requiresUpdate);
  }

  cancel() {
    this.closeWindow(false);
  }
}
