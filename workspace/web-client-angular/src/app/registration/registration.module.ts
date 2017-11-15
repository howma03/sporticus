import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './register/register.component';
import {EboxModule, WizardModule} from "@ux-aspects/ux-aspects";
import {MatButtonModule, MatInputModule} from "@angular/material";
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    EboxModule,
    WizardModule
  ],
  declarations: [RegisterComponent]
})
export class RegistrationModule { }
