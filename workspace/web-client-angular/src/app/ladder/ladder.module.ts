import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LadderComponent} from './ladder/ladder.component';
import {MyLaddersComponent} from './my-ladders/my-ladders.component';
import {ServicesModule} from "../services/services.module";
import {
  MatDialogModule, MatFormFieldModule, MatGridListModule, MatTableModule, MatTooltipModule
} from "@angular/material";
import {LadderDialogComponent} from './ladder-dialog/ladder-dialog.component';
import {HoverActionModule, SparkModule} from "@ux-aspects/ux-aspects";
import {ChallengeModule} from "../challenge/challenge.module";
import {MyLaddersWidgetComponent} from './my-ladders-widget/my-ladders-widget.component';
import {SharedModule} from '../shared/shared.module';

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
    MatTooltipModule,
    SharedModule,
    SparkModule
  ],
  exports: [MyLaddersComponent, MyLaddersWidgetComponent],
  entryComponents: [
    LadderDialogComponent
  ],
  declarations: [LadderComponent, MyLaddersComponent, LadderDialogComponent, MyLaddersWidgetComponent]
})
export class LadderModule {
}
