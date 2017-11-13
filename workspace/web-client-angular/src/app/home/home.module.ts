import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import {MatGridListModule, MatTableModule} from '@angular/material';
import { FutureMatchesTableComponent } from './future-matches-table/future-matches-table.component';
import {PreviousMatchesTableComponent} from './previous-matches-table/previous-matches-table.component';
import {AvailableChallengesTableComponent} from './available-challenges-table/available-challenges-table.component';
import {MyChallengesTableComponent} from './my-challenges-table/my-challenges-table.component';
import {CheckboxModule, DashboardModule} from '@ux-aspects/ux-aspects';
import {MyTeamsTableComponent} from './my-teams-table/my-teams-table.component';
import {LogService} from './home/log.service';
import { RestExampleComponent } from './rest-example/rest-example.component';
import {RestService} from './rest-example/rest.service';

@NgModule({
  imports: [
    CheckboxModule,
    CommonModule,
    DashboardModule,
    MatTableModule,
    MatGridListModule
  ],
  exports: [
    HomeComponent
  ],
  declarations: [
    HomeComponent,
    FutureMatchesTableComponent,
    PreviousMatchesTableComponent,
    AvailableChallengesTableComponent,
    MyChallengesTableComponent,
    MyTeamsTableComponent,
    RestExampleComponent
  ],
  providers: [
    LogService,
    RestService
  ]
})
export class HomeModule { }
