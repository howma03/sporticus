import {Component, Input, OnInit} from '@angular/core';
import {GroupMemberService} from '../../../../services/group-member.service';
import {isNumber} from 'util';
import {Group} from '../../../../services/organisation-groups.service';
import {AddGroupMemberDialogComponent} from '../add-group-member-dialog/add-group-member-dialog.component';
import {MatDialog} from '@angular/material';
import {User} from '../../../../services/users.service';
import {ConfirmDialogComponent} from '../../../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-manage-group-members',
  templateUrl: './manage-group-members.component.html',
  styleUrls: ['./manage-group-members.component.css']
})
export class ManageGroupMembersComponent implements OnInit {
  get group(): Group {
    return this._group;
  }

  @Input()
  set group(value: Group) {
    this._group = value;
    this.getGroupMembers();
  }

  @Input()
  public organisationId: number;

  get groupId(): number {
    return this._group ? this.group.id : -1;
  }


  private _group: Group;

  public members: User[] = [];

  constructor(private dialog: MatDialog,
              public groupMemberService: GroupMemberService
  ) {
  }

  ngOnInit() {
    this.getGroupMembers();
  }

  getGroupMembers() {
    const groupId = this.groupId;
    if (!isNumber(groupId) || groupId < 0) {
      return;
    }
    this.groupMemberService.retrieveAll(groupId).subscribe(members => {
      this.members = members;
    });
  }


  showAddMemberDialog() {
    // this.dialogRef =
    this.dialog.open(AddGroupMemberDialogComponent, {
      data: {
        groupId: this.groupId,
        organisationId: this.organisationId,
        members: this.members
      }
    });
  }

  openDeleteDialog(user: User) {
    const title = 'Confirm Removal';
    const description = `Are you sure you want to remove the user from your organisation? - ${user.firstName} ${user.lastName}`;

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: title,
        description: description,
        organisationName: user.name
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.removeUser(user);
      }
    });
  }

  removeUser(user: User) {
    this.groupMemberService.removeOne(this.groupId, user).subscribe(() => this.getGroupMembers());
  }
}
