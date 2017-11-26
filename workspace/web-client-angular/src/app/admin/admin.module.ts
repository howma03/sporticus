import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminMainComponent} from './admin-main/admin-main.component';
import {AdminRoutingModule} from "./admin-routing.module";
import {EboxModule, PageHeaderModule} from "@ux-aspects/ux-aspects";
import {AdminUsersModule} from "./admin-users/admin-users.module";
import {MatTabsModule} from '@angular/material/tabs';
import {AdminOrganisationsModule} from "./admin-organisations/admin-organisations.module";
import {SharedModule} from "../shared/shared.module";

@NgModule({
  imports: [
    EboxModule,
    CommonModule,
    PageHeaderModule,
    AdminUsersModule,
    AdminOrganisationsModule,
    MatTabsModule,
    SharedModule,
    //Keep Routing Modules last
    AdminRoutingModule
  ],
  exports: [],
  entryComponents: [],
  declarations: [AdminMainComponent],
})
export class AdminModule {
}
