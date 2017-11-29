import {Component, Input, OnInit} from '@angular/core';
import {SendInviteDialogComponent} from '../send-invite-dialog/send-invite-dialog.component';
import {MatDialog} from '@angular/material';
import {User} from '../../../../services/users.service';
import {OrganisationUsersService} from '../organisation-users.service';
import {ConfirmDialogComponent} from '../../../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-manage-memberships',
  templateUrl: './manage-memberships.component.html',
  styleUrls: ['./manage-memberships.component.css'],
})
export class ManageMembershipsComponent implements OnInit {

  @Input()
  organisationId: number;
  users: User[] = [];

  constructor(private dialog: MatDialog, private membersService: OrganisationUsersService) {
  }

  ngOnInit() {
    this.membersService.retrieveAll(this.organisationId).subscribe(userList => this.users = userList.data);
  }

  openInviteDialog() {
    const dialogRef = this.dialog.open(SendInviteDialogComponent, {
      data: {
        organisationId: this.organisationId
      }
    });
  }

  openDeleteDialog(item: User) {
    const title = 'Confirm Removal';
    const description = `Are you sure you want to delete the user - '${item.firstName} ${item.lastName}`;

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: title,
        description: description,
        organisationName: item.name
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.deleteUser(item.id);
      }
    });
  }

  deleteUser(userId: number) {
    this.membersService.removeOne(this.organisationId, userId);
  }
}
