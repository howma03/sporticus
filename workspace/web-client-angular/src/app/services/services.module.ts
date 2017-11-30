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
import {PushService} from "./push.service";
import {EventService} from './event.service';
import {OrganisationGroupsService} from "./organisation-groups.service";
import {GroupMemberService} from "./group-member.service";
import {GroupEventService} from "./group-event.service";

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
  ],
  providers: [
    ChallengeService,
    ErrorHandlingService,
    EventService,
    GroupMemberService,
    GroupEventService,
    UsersService,
    RegistrationService,
    OrganisationService,
    OrganisationGroupsService,
    PushService,
    LadderService,
    ProfileService,
    AgendaService,
    GroupEventService
  ],
  declarations: [
  ]
})
export class ServicesModule { }
