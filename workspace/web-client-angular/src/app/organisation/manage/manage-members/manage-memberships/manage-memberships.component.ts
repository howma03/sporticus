import {Component, Input, OnInit} from '@angular/core';
import {SendInviteDialogComponent} from '../send-invite-dialog/send-invite-dialog.component';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-manage-memberships',
  templateUrl: './manage-memberships.component.html',
  styleUrls: ['./manage-memberships.component.css'],
})
export class ManageMembershipsComponent implements OnInit {

  @Input()
  organisationId: number;

  constructor(private dialog: MatDialog) {
  }

  ngOnInit() {
  }

  openInviteDialog() {
    const dialogRef = this.dialog.open(SendInviteDialogComponent, {
      data: {
        organisationId: this.organisationId
      }
    });
  }
}
