import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageMainComponent } from './manage-main/manage-main.component';
import {ManageRoutingModule} from "./manage-routing.module";
import {EboxModule, PageHeaderModule} from "@ux-aspects/ux-aspects";
import {ManageUsersModule} from "./manage-users/manage-users.module";
import {MatTabsModule} from '@angular/material/tabs';
import {ManageOrganisationsModule} from "./manage-organisations/manage-organisations.module";
import {DeletePromptComponent} from './delete-prompt/delete-prompt.component';

@NgModule({
  imports: [
    EboxModule,
    CommonModule,
    PageHeaderModule,
    ManageRoutingModule,
    ManageUsersModule,
    ManageOrganisationsModule,
    MatTabsModule
  ],
  entryComponents: [
    DeletePromptComponent
  ],
  declarations: [ManageMainComponent, DeletePromptComponent]
})
export class ManageModule { }
