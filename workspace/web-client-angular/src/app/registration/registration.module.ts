import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './register/register.component';
import {WizardModule} from "@ux-aspects/ux-aspects";

@NgModule({
  imports: [
    CommonModule,
    WizardModule
  ],
  declarations: [RegisterComponent]
})
export class RegistrationModule { }
