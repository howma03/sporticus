import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ManageMainComponent} from "./manage-main/manage-main.component";

const manageRoutes: Routes = [
  {
    path: 'manage/main',
    component: ManageMainComponent,
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(
      manageRoutes
    )
  ],
  exports: [
    RouterModule
  ]
})
export class ManageRoutingModule {
}
