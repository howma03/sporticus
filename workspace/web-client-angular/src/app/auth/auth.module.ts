import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AuthService} from "./auth.service";
import {AuthGuard} from "./auth.guard";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: []
})
export class AuthModule {
  public static forRoot(): ModuleWithProviders {
    return {
      ngModule: AuthModule,
      providers: [
        [AuthService, AuthGuard],
      ]
    };
  }
}
