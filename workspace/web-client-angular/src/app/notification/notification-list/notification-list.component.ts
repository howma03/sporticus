import { Component, OnInit } from '@angular/core';
import {NotificationService, Notification} from "../notification.service";
import {PushService} from "../../services/push.service";

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.css']
})
export class NotificationListComponent implements OnInit {

  constructor(
    private notificationService: NotificationService,
    private pushService: PushService
  ) { }

  ngOnInit() {
    this.updateOrganisationDetails();

    this.pushService.registerForEvents().subscribe((data)=>{
      this.updateOrganisationDetails();
    });
  }

  notifications = [];

  updateOrganisationDetails() {
    this.notificationService.retrieveAll()
      .map(list=>list.data)
      .subscribe((data)=>{
        this.notifications = data;
      });
  }
}
