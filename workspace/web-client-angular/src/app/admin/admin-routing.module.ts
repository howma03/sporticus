import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminMainComponent} from "./admin-main/admin-main.component";

const manageRoutes: Routes = [
  {
    path: 'admin/main',
    component: AdminMainComponent,
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
export class AdminRoutingModule {
}
