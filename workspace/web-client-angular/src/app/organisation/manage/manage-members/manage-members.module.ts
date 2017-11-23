import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageMembershipsComponent} from './manage-memberships/manage-memberships.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [ManageMembershipsComponent],
  declarations: [ManageMembershipsComponent]
})
export class ManageMembersModule {
}
