import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrganisationTableComponent } from './organisation-table/organisation-table.component';
import {MatGridListModule, MatTableModule} from "@angular/material";
import {ServicesModule} from "../../services/services.module";

@NgModule({
  imports: [
    CommonModule,
    MatTableModule,
    MatGridListModule,
    ServicesModule
  ],
  exports: [
    OrganisationTableComponent
  ],
  declarations: [OrganisationTableComponent]
})
export class ManageOrganisationsModule { }
