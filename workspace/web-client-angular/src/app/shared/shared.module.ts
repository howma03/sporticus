import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ConfirmDialogComponent} from './confirm-dialog/confirm-dialog.component';
import {MatButtonModule, MatDialogModule, MatProgressSpinnerModule, MatToolbarModule} from '@angular/material';
import {WidgetComponent} from "./widget/widget.component";

@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatToolbarModule,
    MatProgressSpinnerModule
  ],
  exports: [
    WidgetComponent
  ],
  declarations: [ConfirmDialogComponent, WidgetComponent],
  entryComponents: [ConfirmDialogComponent]
})
export class SharedModule { }
