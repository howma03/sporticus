import {Component, Inject, Input, OnInit} from '@angular/core';
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


  makeUserChange() {
    let user: Event = {
      id: this.details.id,
      name: this.details.name
    };

    if (this.editMode === true) {
      this.editUser(user);
    } else {
      this.addUser(user);
    }
  }

  addUser(user) {
    this.groupEventService.createOne(this.group.id, user)
      .subscribe(success => {
        if (success) {
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  editUser(user) {
    this.groupEventService.updateOne(this.group.id, user.id, user)
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


