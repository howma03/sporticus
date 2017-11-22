import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageMainComponent } from './manage-main/manage-main.component';
import {ManageRoutingModule} from "./manage-routing.module";
import {EboxModule, PageHeaderModule} from "@ux-aspects/ux-aspects";
import {ManageUsersModule} from "./manage-users/manage-users.module";
import {MatTabsModule} from '@angular/material/tabs';
import {ManageOrganisationsModule} from "./manage-organisations/manage-organisations.module";
import {SharedModule} from "../shared/shared.module";

@NgModule({
  imports: [
    EboxModule,
    CommonModule,
    PageHeaderModule,
    ManageRoutingModule,
    ManageUsersModule,
    ManageOrganisationsModule,
    MatTabsModule,
    SharedModule
  ],
  exports: [
  ],
  entryComponents: [
  ],
  declarations: [ManageMainComponent]
})
export class ManageModule { }
