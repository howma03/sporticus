import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-unavailable-dialog',
  templateUrl: './unavailable-dialog.component.html',
  styleUrls: ['./unavailable-dialog.component.css']
})
export class UnavailableDialogComponent implements OnInit {

  private date: Date;
  private event: Event;

  constructor(public dialogRef: MatDialogRef<UnavailableDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.date = this.data.date;
  }

  ngOnInit() {
  }

  close(date) {
    this.date = date;
    this.dialogRef.close(event);
  }

}
