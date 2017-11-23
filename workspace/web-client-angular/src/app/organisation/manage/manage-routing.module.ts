import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ManageMainComponent} from "./manage-main/manage-main.component";
import {ManageOrganisationComponent} from "./manage-organisation/manage-organisation.component";
import {ManageMembershipsComponent} from "./manage-members/manage-memberships/manage-memberships.component";

const manageRoutes: Routes = [
  {
    path: '',
    component: ManageMainComponent,
    children: [
      {
        path: 'organisation',
        component: ManageOrganisationComponent
      },
      {
        path: 'members',
        component: ManageMembershipsComponent,
      },
      {
        path: 'competitions',
        component: ManageOrganisationComponent
      },
      {
        path: '',
        redirectTo: 'organisation',
        pathMatch: 'full'
      }
    ]
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
export class MangeRoutingModule {
}
