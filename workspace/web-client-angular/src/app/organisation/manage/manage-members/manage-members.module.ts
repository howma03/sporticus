import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageMembershipsComponent} from './manage-memberships/manage-memberships.component';
import {SendInviteComponent} from './send-invite/send-invite.component';
import {ViewRequestsComponent} from './view-requests/view-requests.component';
import {ReactiveFormsModule} from '@angular/forms';
import {
  MatButtonModule,
  MatChipsModule,
  MatDialogModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatSelectModule,
  MatTabsModule
} from '@angular/material';
import {ServicesModule} from '../../../services/services.module';
import {SendInviteDialogComponent} from './send-invite-dialog/send-invite-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,

    MatTabsModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatMenuModule,
    MatDialogModule,

    ServicesModule
  ],
  exports: [ManageMembershipsComponent],
  entryComponents: [SendInviteDialogComponent],
  declarations: [ManageMembershipsComponent, SendInviteComponent, ViewRequestsComponent, SendInviteDialogComponent]
})
export class ManageMembersModule {
}
