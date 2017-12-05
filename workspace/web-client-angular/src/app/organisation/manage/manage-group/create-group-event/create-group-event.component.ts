import {Component, Inject, OnInit} from '@angular/core';
import {Event, GroupEventService} from "../../../../services/group-event.service";
import {ErrorHandlingService} from "../../../../services/error-handling.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Group} from "../../../../services/organisation-groups.service";

@Component({
  selector: 'app-create-group-event',
  templateUrl: './create-group-event.component.html',
  styleUrls: ['./create-group-event.component.css']
})
export class CreateGroupEventComponent implements OnInit {

  details: Event = <Event>{};
  submitText = 'Test';
  editMode = false;

  group: Group;

  constructor(
    public groupEventService: GroupEventService,
    private errorHandlingService: ErrorHandlingService,
    public dialogRef: MatDialogRef<CreateGroupEventComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    this.group = this.data.group;
  }

  submit() {
    const event: Event = {
      id: this.details.id,
      name: this.details.name,
      type: this.details.type
    };

    if (this.editMode === true) {
      this.performUpdateOperation(event);
    } else {
      this.performAddOperation(event);
    }
  }

  performAddOperation(entity) {
    this.groupEventService.createOne(this.group.id, entity)
      .subscribe(success => {
        if (success) {
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  performUpdateOperation(entity) {
    this.groupEventService.updateOne(this.group.id, entity.id, entity)
      .subscribe(success => {
        if (success) {
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  closeWindow(requiresUpdate: boolean) {
    this.dialogRef.close(requiresUpdate);
  }

  cancel() {
    this.closeWindow(false);
  }

}


