import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-send-invite-dialog',
  templateUrl: './send-invite-dialog.component.html',
  styleUrls: ['./send-invite-dialog.component.css'],
})
export class SendInviteDialogComponent implements OnInit {


  organisationId: number;

  constructor(public dialogRef: MatDialogRef<SendInviteDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.organisationId = this.data.organisationId;
  }

  ngOnInit() {
  }

  close() {
    this.dialogRef.close();
  }

}
