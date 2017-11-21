import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrganisationHomeComponent } from './organisation-home/organisation-home.component';
import {OrganisationRoutingModule} from "./organisation-routing.module";
import {EboxModule} from "@ux-aspects/ux-aspects";

@NgModule({
  imports: [
    CommonModule,
    EboxModule,
    OrganisationRoutingModule
  ],
  declarations: [OrganisationHomeComponent]
})
export class OrganisationModule { }
