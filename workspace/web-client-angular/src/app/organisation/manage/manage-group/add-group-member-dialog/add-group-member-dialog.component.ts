import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {User} from '../../../../services/users.service';

@Component({
  selector: 'app-add-group-member-dialog',
  templateUrl: './add-group-member-dialog.component.html',
  styleUrls: ['./add-group-member-dialog.component.css']
})
export class AddGroupMemberDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddGroupMemberDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.groupId = data.groupId;
    this.organisationId = data.organisationId;
    this.members = data.members;
  }

  public groupId: number;
  public organisationId: number;
  public members: User[];

  ngOnInit() {
  }

  close() {
    this.dialogRef.close();
  }

}
