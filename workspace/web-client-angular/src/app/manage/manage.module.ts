import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageMainComponent } from './manage-main/manage-main.component';
import {ManageRoutingModule} from "./manage-routing.module";
import {PageHeaderModule} from "@ux-aspects/ux-aspects";
import {ManageUsersModule} from "./manage-users/manage-users.module";

@NgModule({
  imports: [
    CommonModule,
    PageHeaderModule,
    ManageRoutingModule,
    ManageUsersModule
  ],
  declarations: [ManageMainComponent]
})
export class ManageModule { }
