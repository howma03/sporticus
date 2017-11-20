import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LadderComponent} from './ladder/ladder.component';
import {MyLaddersComponent} from './my-ladders/my-ladders.component';
import {ServicesModule} from "../services/services.module";
import {MatDialogModule, MatFormFieldModule, MatGridListModule, MatTableModule} from "@angular/material";
import {LadderDialogComponent} from './ladder-dialog/ladder-dialog.component';
import {HoverActionModule, SparkModule} from "@ux-aspects/ux-aspects";
import {ChallengeModule} from "../challenge/challenge.module";

@NgModule({
  imports: [
    ChallengeModule,
    CommonModule,
    HoverActionModule,
    ServicesModule,
    MatDialogModule,
    MatTableModule,
    MatGridListModule,
    MatFormFieldModule,
    SparkModule
  ],
  exports: [MyLaddersComponent],
  entryComponents: [
    LadderDialogComponent
  ],
  declarations: [LadderComponent, MyLaddersComponent, LadderDialogComponent]
})
export class LadderModule {
}
