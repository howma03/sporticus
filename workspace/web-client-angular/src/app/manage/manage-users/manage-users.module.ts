import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserTableComponent } from './user-table/user-table.component';
import {MatGridListModule, MatTableModule} from '@angular/material';
import {ServicesModule} from "../../services/services.module";

@NgModule({
  imports: [
    CommonModule,
    MatTableModule,
    MatGridListModule,
    ServicesModule
  ],
  exports: [
    UserTableComponent
  ],
  declarations: [UserTableComponent],
  providers: [

  ]
})
export class ManageUsersModule { }
