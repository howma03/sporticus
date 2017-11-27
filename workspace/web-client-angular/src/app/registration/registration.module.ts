import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegisterComponent} from './register/register.component';
import {EboxModule, WizardModule} from "@ux-aspects/ux-aspects";
import {MatButtonModule, MatInputModule, MatSnackBarModule} from "@angular/material";
import {FormsModule} from "@angular/forms";
import {CaptchaModule} from 'primeng/primeng';

@NgModule({
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    EboxModule,
    WizardModule,
    MatSnackBarModule,
    CaptchaModule
  ],
  declarations: [RegisterComponent]
})
export class RegistrationModule { }
