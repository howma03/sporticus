import {Component, Inject, OnInit} from '@angular/core';
import {NotificationInterface, NotificationService} from '../notification.service';
import {ErrorHandlingService} from '../../services/error-handling.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {MessageService} from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-manage-notification',
  templateUrl: './manage-notification.component.html',
  styleUrls: ['./manage-notification.component.css']
})
export class ManageNotificationComponent implements OnInit {

  protected submitText = 'SEND';
  protected creationDescription: String = 'To send a new notification please enter it below';
  protected details: NotificationInterface = <NotificationInterface>{};

  constructor(
    private notificationService: NotificationService,
    private errorHandlingService: ErrorHandlingService,
    private messageService: MessageService,
    public dialogRef: MatDialogRef<ManageNotificationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
  }

  addNotification() {
    const notification: NotificationInterface = {
      title: 'Broadcast',
      text: this.details.text
    };

    this.notificationService.createOne(notification)
      .subscribe(success => {
        if (success) {
          this.messageService.add({
            severity: 'success',
            summary: 'Notification Sent',
            detail: 'Notification sent successfully'
          });
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  cancel() {
    this.closeWindow(false);
  }

  closeWindow(requiresUpdate: boolean) {
    this.dialogRef.close(requiresUpdate);
  }
}
