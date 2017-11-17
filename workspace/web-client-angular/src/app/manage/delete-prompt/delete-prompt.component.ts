import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-delete-prompt',
  templateUrl: './delete-prompt.component.html',
  styleUrls: ['./delete-prompt.component.css'],
})
export class DeletePromptComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeletePromptComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
  }

  closeDialog(confirmedState): void {
    this.dialogRef.close(confirmedState);
  }

}
