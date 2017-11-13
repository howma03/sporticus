import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './register/register.component';
import {WizardModule} from "@ux-aspects/ux-aspects";
import {MatInputModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    MatInputModule,
    WizardModule
  ],
  declarations: [RegisterComponent]
})
export class RegistrationModule { }
