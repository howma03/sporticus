import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationListComponent } from './notification-list/notification-list.component';
import {NotificationService} from "./notification.service";
import {ServicesModule} from "../services/services.module";
import { NotificationManagementComponent } from './notification-management/notification-management.component';
import { ManageNotificationComponent } from './manage-notification/manage-notification.component';
import {MatFormFieldModule, MatTooltipModule} from "@angular/material";
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    ServicesModule,
    MatFormFieldModule,
    FormsModule,
    MatTooltipModule
  ],
  entryComponents: [
    ManageNotificationComponent
  ],
  exports: [
    NotificationListComponent,
    ManageNotificationComponent,
    NotificationManagementComponent,
  ],
  declarations: [
    NotificationListComponent,
    ManageNotificationComponent,
    NotificationManagementComponent,
    ManageNotificationComponent
  ],
  providers: [
    NotificationService,
    NotificationListComponent
  ]
})
export class NotificationModule { }


