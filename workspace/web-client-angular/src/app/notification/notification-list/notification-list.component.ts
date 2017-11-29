import { Component, OnInit } from '@angular/core';
import {NotificationService, NotificationInterface} from '../notification.service';
import {PushService} from '../../services/push.service';
import {MatDialog} from '@angular/material';
import {ManageNotificationComponent} from '../manage-notification/manage-notification.component';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.css']
})
export class NotificationListComponent implements OnInit {

  notifications = [];

  constructor(
    private notificationService: NotificationService,
    private pushService: PushService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.updateNotificationDetails();

    this.pushService.registerForEvents().subscribe((data) => {
      this.updateNotificationDetails();
    });
  }


  updateNotificationDetails() {
    this.notificationService.retrieveAll()
      .map(list => list.data)
      .subscribe((data) => {
        this.notifications = data;
      });
  }

  markRead(notification) {
    notification.status = 'READ';
    this.notificationService.updateOne(notification.id, notification);
  }

  markUnread(notification) {
    notification.status = 'UNREAD';
    this.notificationService.updateOne(notification.id, notification);
  }


  getTooltip(item ) {
    const status = item.status;
    if (status === 'UNREAD') {
      return 'Mark as read';
    } else {
      return 'Mark as un-read';
    }
  }

  public openModal(itemId): void {
    const item = this.notifications.find(item => item.id === itemId);

    const dialogRef = this.dialog.open(ManageNotificationComponent, {
      data: {
        item: item
      },
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe((updateRequired) => {
      if (updateRequired) {
        this.updateNotificationDetails();
      }
    });
  }
}
