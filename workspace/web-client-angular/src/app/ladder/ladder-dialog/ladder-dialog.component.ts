import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Ladder} from "../../services/ladder.service";

@Component({
  selector: 'app-ladder-dialog',
  templateUrl: './ladder-dialog.component.html',
  styleUrls: ['./ladder-dialog.component.css'],
})
export class LadderDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<LadderDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.ladder = data.ladder;
  }

  ngOnInit() {
  }

  public ladder: Ladder;

  onNoClick(): void {
    this.dialogRef.close();
  }

}
