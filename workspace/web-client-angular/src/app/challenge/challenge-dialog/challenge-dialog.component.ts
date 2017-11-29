import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Ladder, LadderUser} from "../../services/ladder.service";

@Component({
  selector: 'app-challenge-dialog',
  templateUrl: './challenge-dialog.component.html',
  styleUrls: ['./challenge-dialog.component.css']
})
export class ChallengeDialogComponent implements OnInit {

  private rung: LadderUser;
  private ladder: Ladder;
  private startDate: Date;

  constructor(public dialogRef: MatDialogRef<ChallengeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.rung = this.data.rung;
    this.ladder = this.data.ladder;
    this.startDate = this.data.startDate;
  }

  ngOnInit() {
  }

  close(rung) {
    this.rung = rung;
    this.dialogRef.close(rung);
  }

}
