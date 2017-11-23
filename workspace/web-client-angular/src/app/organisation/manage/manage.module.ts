import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageMainComponent} from './manage-main/manage-main.component';
import {ManageOrganisationComponent} from './manage-organisation/manage-organisation.component';
import {MatTabsModule} from "@angular/material";
import {ManageCompetitionsModule} from "./manage-competitions/manage-competitions.module";
import {ManageMembersModule} from "./manage-members/manage-members.module";
import {MangeRoutingModule} from "./manage-routing.module";

@NgModule({
  imports: [
    MatTabsModule,
    CommonModule,
    ManageCompetitionsModule,
    ManageMembersModule,
    //Keep Routing Modules last
    MangeRoutingModule
  ],
  exports: [ManageMainComponent],
  declarations: [ManageMainComponent, ManageOrganisationComponent]
})
export class ManageModule {
}
