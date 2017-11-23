import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AppendOrdinalPipe} from './append-ordinal.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [AppendOrdinalPipe],
  declarations: [AppendOrdinalPipe],
  providers: [AppendOrdinalPipe]
})
export class UtilModule {
}
