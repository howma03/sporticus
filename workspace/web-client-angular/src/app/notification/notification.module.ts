import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationListComponent } from './notification-list/notification-list.component';
import {NotificationService} from "./notification.service";
import {ServicesModule} from "../services/services.module";

@NgModule({
  imports: [
    CommonModule,
    ServicesModule,
  ],
  exports: [
    NotificationListComponent
  ],
  declarations: [
    NotificationListComponent
  ],
  providers: [
    NotificationService,
    NotificationListComponent
  ]
})
export class NotificationModule { }
