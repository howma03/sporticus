import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {User} from '../../services/users.service';

@Component({
  selector: 'app-profile-dialog',
  templateUrl: './profile-dialog.component.html',
  styleUrls: ['./profile-dialog.component.css'],
})
export class ProfileDialogComponent implements OnInit {

  user: User;

  constructor(public dialogRef: MatDialogRef<ProfileDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.user = this.data.user;
  }


  ngOnInit() {
  }

  close(user) {
    this.user = user;
    this.dialogRef.close(user);
  }

}
