import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserTableComponent} from './user-table/user-table.component';
import {MatDialogModule, MatGridListModule, MatTableModule} from '@angular/material';
import {ServicesModule} from "../../services/services.module";
import {UserComponent} from './user/user.component';
import {HoverActionModule} from "@ux-aspects/ux-aspects";
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatGridListModule,
    HoverActionModule,
    ServicesModule,
    MatDialogModule
  ],
  exports: [
    UserTableComponent
  ],
  entryComponents: [
    UserComponent
  ],
  declarations: [UserTableComponent, UserComponent],
  providers: [
  ]
})
export class ManageUsersModule { }
