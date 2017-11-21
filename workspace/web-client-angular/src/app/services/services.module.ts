import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UsersService} from "./users.service";
import {OrganisationService} from "./organisation.service";
import {RegistrationService} from "./registration.service";
import {ErrorHandlingService} from "./error-handling.service";
import {LadderService} from "./ladder.service";
import {ProfileService} from "./profile.service";
import {ChallengeService} from "./challenge.service";
import {AgendaService} from "./agenda.service";

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
  ],
  providers: [
    ChallengeService,
    ErrorHandlingService,
    UsersService,
    RegistrationService,
    OrganisationService,
    LadderService,
    ProfileService,
    AgendaService
  ],
  declarations: [
  ]
})
export class ServicesModule { }
