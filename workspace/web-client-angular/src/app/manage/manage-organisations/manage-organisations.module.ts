///<reference path="../../../../node_modules/@ux-aspects/ux-aspects/components/spark/spark.component.d.ts"/>
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrganisationTableComponent } from './organisation-table/organisation-table.component';
import {MatDialogModule, MatGridListModule, MatTableModule} from "@angular/material";
import {ServicesModule} from "../../services/services.module";
import {HoverActionModule, SparkModule} from "@ux-aspects/ux-aspects";
import { OrganisationComponent } from './organisation/organisation.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatGridListModule,
    ServicesModule,
    SparkModule,
    HoverActionModule,
    MatDialogModule
  ],
  exports: [
    OrganisationTableComponent
  ],
  entryComponents: [
    OrganisationComponent
  ],
  declarations: [OrganisationTableComponent, OrganisationComponent]
})
export class ManageOrganisationsModule { }
