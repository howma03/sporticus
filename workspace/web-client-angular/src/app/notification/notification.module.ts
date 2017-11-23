import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationListComponent } from './notification-list/notification-list.component';
import {NotificationService} from "./notification.service";
import {ServicesModule} from "../services/services.module";
import { NotificationManagementComponent } from './notification-management/notification-management.component';

@NgModule({
  imports: [
    CommonModule,
    ServicesModule,
  ],
  exports: [
    NotificationListComponent,
    NotificationManagementComponent
  ],
  declarations: [
    NotificationListComponent,
    NotificationManagementComponent
  ],
  providers: [
    NotificationService,
    NotificationListComponent
  ]
})
export class NotificationModule { }
