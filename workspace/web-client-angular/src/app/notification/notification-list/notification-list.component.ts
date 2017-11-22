import { Component, OnInit } from '@angular/core';
import {NotificationService} from "../notification.service";

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.css']
})
export class NotificationListComponent implements OnInit {

  constructor(
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.updateOrganisationDetails();
  }

  notifications = [];

  updateOrganisationDetails() {
    this.notificationService.retrieveAll()
      .map(list=>list.data)
      .subscribe((data)=>{
        debugger;
        this.notifications = data;
      });
  }
}
