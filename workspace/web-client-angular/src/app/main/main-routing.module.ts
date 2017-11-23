import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {HomeComponent} from "../home/home/home.component";
import {TrackCompetitionsComponent} from "../track-competitions/track-competitions/track-competitions.component";
import {MainComponent} from "./main/main.component";
import {AuthGuard} from "../login/auth.guard";
import {AdminMainComponent} from "../admin/admin-main/admin-main.component";
import {ManageMainComponent} from "../organisation/manage/manage-main/manage-main.component";
import {CalenderMainComponent} from "../calendar/calender-main/calender-main.component";

const mainRoutes: Routes = [
  {
    path: 'main',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'admin', redirectTo: 'admin-users'},
      {
        path: 'admin-users',
        component: AdminMainComponent,
        data: {
          selectedIndex: 0
        }
      },
      {
        path: 'admin-organisations',
        component: AdminMainComponent,
        data: {
          selectedIndex: 1
        }
      },

      {path: 'manage', redirectTo: 'manage-organisation'},
      {
        path: 'manage-organisation',
        component: ManageMainComponent,
        data: {
          selectedIndex: 0
        }
      },
      {
        path: 'manage-members',
        component: ManageMainComponent,
        data: {
          selectedIndex: 1
        }
      },
      {
        path: 'manage-competition',
        component: ManageMainComponent,
        data: {
          selectedIndex: 2
        }
      },

      {path: 'track-competitions', component: TrackCompetitionsComponent},
      {path: 'calendar', component: CalenderMainComponent},
      {path: '', redirectTo: '/main/home', pathMatch: 'full'},
      {path: '**', redirectTo: '/main/home', pathMatch: 'full'}
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      mainRoutes,
      {enableTracing: true} // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})
export class MainRoutingModule {
}
