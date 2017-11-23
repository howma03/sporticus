import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminMainComponent} from "./admin-main/admin-main.component";
import {UserTableComponent} from "./admin-users/user-table/user-table.component";
import {OrganisationTableComponent} from "./admin-organisations/organisation-table/organisation-table.component";

const adminRoutes: Routes = [
  {
    path: '',
    component: AdminMainComponent,
    children: [
      {
        path: 'users',
        component: UserTableComponent,
      },
      {
        path: 'organisations',
        component: OrganisationTableComponent
      },
      {
        path: '',
        redirectTo: 'users',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(
      adminRoutes
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AdminRoutingModule {
}
