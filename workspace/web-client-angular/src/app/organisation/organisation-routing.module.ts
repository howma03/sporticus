import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrganisationHomeComponent} from "./organisation-home/organisation-home.component";

const manageRoutes: Routes = [
  {
    //No organisation set... this could be a worry?
    path: 'organisation',
    component: OrganisationHomeComponent,
  },
  {
    path: 'organisation/:fragmentUrl',
    component: OrganisationHomeComponent,
  },
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
export class OrganisationRoutingModule {
}
