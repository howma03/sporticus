import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileComponent} from './profile/profile.component';
import {ServicesModule} from "../services/services.module";
import {ProfileDialogComponent} from "./profile-dialog/profile-dialog.component";
import {MatButtonModule, MatDialogModule, MatInputModule} from "@angular/material";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    ServicesModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
  ],
  declarations: [ProfileComponent, ProfileDialogComponent],
  exports: [ProfileDialogComponent],
  entryComponents: [ProfileDialogComponent]
})
export class ProfileModule {
}
