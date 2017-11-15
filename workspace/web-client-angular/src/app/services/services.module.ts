import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UsersService} from "./users.service";
import {OrganisationService} from "./organisation.service";
import {RegistrationService} from "./registration.service";
import {ErrorHandlingService} from "./error-handling.service";

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
  ],
  providers: [
    ErrorHandlingService,
    UsersService,
    RegistrationService,
    OrganisationService
  ],
  declarations: [
  ]
})
export class ServicesModule { }
