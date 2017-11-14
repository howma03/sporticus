import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './register/register.component';
import {EboxModule, WizardModule} from "@ux-aspects/ux-aspects";
import {MatButtonModule, MatInputModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    EboxModule,
    WizardModule
  ],
  declarations: [RegisterComponent]
})
export class RegistrationModule { }
