import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageMainComponent} from './manage-main/manage-main.component';
import {ManageOrganisationComponent} from './manage-organisation/manage-organisation.component';
import {MatButtonModule, MatInputModule, MatSelectModule, MatTabsModule} from "@angular/material";
import {ManageCompetitionsModule} from "./manage-competitions/manage-competitions.module";
import {ManageMembersModule} from "./manage-members/manage-members.module";
import {MangeRoutingModule} from "./manage-routing.module";
import {ServicesModule} from "../../services/services.module";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,

    MatTabsModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,

    ServicesModule,
    ManageCompetitionsModule,
    ManageMembersModule,
    // Keep Routing Modules last
    MangeRoutingModule
  ],
  exports: [ManageMainComponent],
  declarations: [ManageMainComponent, ManageOrganisationComponent]
})
export class ManageModule {
}
