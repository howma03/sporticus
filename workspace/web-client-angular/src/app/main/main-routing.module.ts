import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {HomeComponent} from "../home/home/home.component";
import {TrackCompetitionsComponent} from "../track-competitions/track-competitions/track-competitions.component";
import {MainComponent} from "./main/main.component";
import {AuthGuard} from "../login/auth.guard";
import {CalendarWrapperComponent} from "../calendar/calendar-wrapper/calendar-wrapper.component";
import {ManageModule} from "../organisation/manage/manage.module";
import {AdminModule} from "../admin/admin.module";

const mainRoutes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {path: '', redirectTo: 'home'},
      {path: 'home', component: HomeComponent},
      {
        path: 'admin',
        loadChildren: () => AdminModule
      },
      {
        path: 'manage',
        loadChildren: () => ManageModule
      },
      {path: 'track-competitions', component: TrackCompetitionsComponent},
      {path: 'calendar', component: CalendarWrapperComponent},
      {path: '**', redirectTo: 'home'}
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(
      mainRoutes
    )
  ],
  exports: [
    RouterModule
  ]
})
export class MainRoutingModule {
}
