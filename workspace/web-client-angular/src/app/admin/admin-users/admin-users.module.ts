import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserTableComponent} from './user-table/user-table.component';
import {MatDialogModule, MatGridListModule, MatTableModule} from '@angular/material';
import {ServicesModule} from "../../services/services.module";
import {UserComponent} from './user/user.component';
import {FormsModule} from "@angular/forms";
import {HoverActionModule} from "@ux-aspects/ux-aspects";
import {GrowlModule} from 'primeng/primeng';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatGridListModule,
    ServicesModule,
    MatDialogModule,
    HoverActionModule,
    GrowlModule
  ],
  exports: [
    UserTableComponent
  ],
  entryComponents: [
    UserComponent
  ],
  declarations: [UserTableComponent, UserComponent]
})
export class AdminUsersModule {
}
