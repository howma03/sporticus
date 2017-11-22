import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home/home.component';
import {DashboardModule, LayoutSwitcherModule} from '@ux-aspects/ux-aspects';
import {ServicesModule} from "../services/services.module";
import {LadderModule} from "../ladder/ladder.module";
import {AgendaComponent} from './agenda/agenda.component';
import {UtilModule} from "../util/util.module"
import {MomentModule} from "angular2-moment";
import {MatButtonToggleModule, MatCardModule} from "@angular/material";
import {FormsModule} from "@angular/forms";
import {SharedModule} from "../shared/shared.module";

@NgModule({
  imports: [
    SharedModule,
    CommonModule,
    FormsModule,
    DashboardModule,
    LadderModule,
    ServicesModule,
    UtilModule,
    MomentModule,
    LayoutSwitcherModule,
    MatCardModule,
    MatButtonToggleModule
  ],
  exports: [
    HomeComponent
  ],
  declarations: [
    HomeComponent,
    AgendaComponent
  ],
  providers: [
  ]
})
export class HomeModule { }
