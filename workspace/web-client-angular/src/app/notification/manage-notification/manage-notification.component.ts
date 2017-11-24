import {Component, Inject, OnInit} from '@angular/core';
import {NotificationInterface, NotificationService} from "../notification.service";
import {ErrorHandlingService} from "../../services/error-handling.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-manage-notification',
  templateUrl: './manage-notification.component.html',
  styleUrls: ['./manage-notification.component.css']
})
export class ManageNotificationComponent implements OnInit {

  submitText: string = "SEND";
  creationDescription: String = "To send a new notification please enter it below";
  details: NotificationInterface = <NotificationInterface>{};

  constructor(
    private notificationService: NotificationService,
    private errorHandlingService: ErrorHandlingService,
    public dialogRef: MatDialogRef<ManageNotificationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
  }

  ngOnInit() {
  }

  addNotification(notification) {
    debugger;

    this.notificationService.createOne(notification)
      .subscribe(success => {
        if (success) {
          alert("Notification successfully sent.");
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
