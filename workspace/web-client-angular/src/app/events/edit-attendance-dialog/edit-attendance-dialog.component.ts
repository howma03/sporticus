import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Attendance} from "../../services/event-attendance.service";

@Component({
  selector: 'app-edit-attendance-dialog',
  templateUrl: './edit-attendance-dialog.component.html',
  styleUrls: ['./edit-attendance-dialog.component.css']
})
export class EditAttendanceDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EditAttendanceDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {


    this.attendance = data.attendance;
    this.event = data.event;
  }

  public attendance: Attendance;
  public event: Event;

  ngOnInit() {
  }

  close() {
    this.dialogRef.close();
  }

}
