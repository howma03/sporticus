import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LadderComponent} from './ladder/ladder.component';
import {MyLaddersComponent} from './my-ladders/my-ladders.component';
import {ServicesModule} from "../services/services.module";
import {MatDialogModule, MatFormFieldModule, MatGridListModule, MatTableModule} from "@angular/material";
import {LadderDialogComponent} from './ladder-dialog/ladder-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    ServicesModule,
    MatDialogModule,
    MatTableModule,
    MatGridListModule,
    MatFormFieldModule
  ],
  exports: [MyLaddersComponent],
  entryComponents: [
    LadderDialogComponent
  ],
  declarations: [LadderComponent, MyLaddersComponent, LadderDialogComponent]
})
export class LadderModule {
}
