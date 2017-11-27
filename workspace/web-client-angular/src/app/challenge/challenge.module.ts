import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChallengeDialogComponent } from './challenge-dialog/challenge-dialog.component';
import { ChallengeFormComponent } from './challenge-form/challenge-form.component';
import {MatButtonModule, MatDialogModule, MatInputModule} from "@angular/material";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule
  ],
  exports: [ChallengeDialogComponent, ReactiveFormsModule],
  declarations: [ChallengeDialogComponent, ChallengeFormComponent],
  entryComponents: [ChallengeDialogComponent]
})
export class ChallengeModule { }
